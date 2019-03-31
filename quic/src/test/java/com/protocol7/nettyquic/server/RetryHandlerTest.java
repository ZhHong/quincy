package com.protocol7.nettyquic.server;

import static java.lang.System.currentTimeMillis;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.protocol7.nettyquic.PipelineContext;
import com.protocol7.nettyquic.TestUtil;
import com.protocol7.nettyquic.addressvalidation.RetryHandler;
import com.protocol7.nettyquic.addressvalidation.RetryToken;
import com.protocol7.nettyquic.protocol.ConnectionId;
import com.protocol7.nettyquic.protocol.PacketNumber;
import com.protocol7.nettyquic.protocol.Payload;
import com.protocol7.nettyquic.protocol.Version;
import com.protocol7.nettyquic.protocol.frames.PingFrame;
import com.protocol7.nettyquic.protocol.packets.InitialPacket;
import com.protocol7.nettyquic.protocol.packets.Packet;
import com.protocol7.nettyquic.protocol.packets.RetryPacket;
import com.protocol7.nettyquic.tls.KeyUtil;
import java.net.InetAddress;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

public class RetryHandlerTest {

  @Mock PipelineContext ctx;
  private RetryToken retryToken =
      new RetryToken(KeyUtil.getPrivateKey("src/test/resources/server.der"));
  private RetryHandler handler = new RetryHandler(retryToken, 10000, TimeUnit.MILLISECONDS);
  private InetAddress address = TestUtil.getTestAddress().getAddress();

  @Before
  public void setUp() {
    initMocks(this);

    when(ctx.getVersion()).thenReturn(Version.DRAFT_18);
    when(ctx.getPeerAddress()).thenReturn(TestUtil.getTestAddress());
  }

  @Test
  public void retry() {
    InitialPacket initialPacket = p(Optional.empty());
    handler.onReceivePacket(initialPacket, ctx);

    ArgumentCaptor<RetryPacket> retryCaptor = ArgumentCaptor.forClass(RetryPacket.class);

    verify(ctx).sendPacket(retryCaptor.capture());

    RetryPacket retry = retryCaptor.getValue();

    assertEquals(initialPacket.getSourceConnectionId(), retry.getDestinationConnectionId());
    // TODO verify token

    // initial packet was not propagated
    verify(ctx, never()).next(any(Packet.class));
  }

  @Test
  public void retryExists() {
    InitialPacket initialPacket = p(of(retryToken.create(address, currentTimeMillis() + 10000)));
    handler.onReceivePacket(initialPacket, ctx);

    // no retry sent
    verify(ctx, never()).sendPacket(any(Packet.class));

    // initial packet propagated
    verify(ctx).next(initialPacket);
  }

  private InitialPacket p(Optional<byte[]> token) {
    return new InitialPacket(
        of(ConnectionId.random()),
        of(ConnectionId.random()),
        Version.DRAFT_18,
        PacketNumber.MIN,
        new Payload(PingFrame.INSTANCE),
        token);
  }
}
