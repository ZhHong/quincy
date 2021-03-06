package com.protocol7.quincy.tls;

import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.protocol7.quincy.FrameSender;
import com.protocol7.quincy.PipelineContext;
import com.protocol7.quincy.connection.State;
import com.protocol7.quincy.netty.QuicBuilder;
import com.protocol7.quincy.protocol.ConnectionId;
import com.protocol7.quincy.protocol.PacketNumber;
import com.protocol7.quincy.protocol.Version;
import com.protocol7.quincy.protocol.frames.CryptoFrame;
import com.protocol7.quincy.protocol.frames.PaddingFrame;
import com.protocol7.quincy.protocol.packets.HandshakePacket;
import com.protocol7.quincy.protocol.packets.InitialPacket;
import com.protocol7.quincy.tls.ServerTlsSession.ServerHelloAndHandshake;
import com.protocol7.quincy.tls.aead.InitialAEAD;
import com.protocol7.quincy.tls.extensions.TransportParameters;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientTlsManagerTest {

  private ConnectionId connectionId = ConnectionId.random();
  private TransportParameters tps = new QuicBuilder().configuration().toTransportParameters();
  private ClientTlsManager manager =
      new ClientTlsManager(connectionId, tps, new NoopCertificateValidator());

  private ServerTlsSession serverTlsSession =
      new ServerTlsSession(
          InitialAEAD.create(connectionId.asBytes(), false),
          tps,
          KeyUtil.getCertsFromCrt("src/test/resources/server.crt"),
          KeyUtil.getPrivateKey("src/test/resources/server.der"));

  @Test
  public void handshake() {
    // start handshake
    final DefaultPromise<Void> handshakeFuture = new DefaultPromise(GlobalEventExecutor.INSTANCE);
    final PipelineContext ctx = mock(PipelineContext.class);
    final FrameSender sender = mock(FrameSender.class);
    final Consumer<State> stateSetter = mock(Consumer.class);
    manager.handshake(State.Started, sender, stateSetter, handshakeFuture);

    final ArgumentCaptor<CryptoFrame> chFrame = ArgumentCaptor.forClass(CryptoFrame.class);

    verify(sender).send(chFrame.capture(), any(PaddingFrame.class));
    verify(stateSetter).accept(State.BeforeHello);

    final byte[] ch = chFrame.getValue().getCryptoData();

    final ServerHelloAndHandshake shah = serverTlsSession.handleClientHello(ch);

    // receive server hello
    final InitialPacket shPacket = ip(shah.getServerHello());
    when(ctx.getState()).thenReturn(State.BeforeHello);
    manager.onReceivePacket(shPacket, ctx);

    verify(ctx, never()).send(any(CryptoFrame.class));
    verify(ctx).setState(State.BeforeHandshake);
    verify(ctx).next(shPacket);

    // receive server handshake
    final HandshakePacket handshakePacket = hp(shah.getServerHandshake());
    when(ctx.getState()).thenReturn(State.BeforeHandshake);
    manager.onReceivePacket(handshakePacket, ctx);

    verify(ctx).send(any(CryptoFrame.class));
    verify(ctx).setState(State.Ready);
    verify(ctx).next(handshakePacket);

    // and we're done
  }

  private InitialPacket ip(final byte[] b) {
    return InitialPacket.create(
        empty(), empty(), PacketNumber.MIN, Version.DRAFT_18, empty(), new CryptoFrame(0, b));
  }

  private HandshakePacket hp(final byte[] b) {
    return HandshakePacket.create(
        empty(), empty(), PacketNumber.MIN, Version.DRAFT_18, new CryptoFrame(0, b));
  }
}
