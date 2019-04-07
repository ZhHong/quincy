package com.protocol7.nettyquic.connection;

import com.protocol7.nettyquic.FrameSender;
import com.protocol7.nettyquic.protocol.ConnectionId;
import com.protocol7.nettyquic.protocol.TransportError;
import com.protocol7.nettyquic.protocol.Version;
import com.protocol7.nettyquic.protocol.frames.Frame;
import com.protocol7.nettyquic.protocol.frames.FrameType;
import com.protocol7.nettyquic.protocol.packets.FullPacket;
import com.protocol7.nettyquic.protocol.packets.Packet;
import com.protocol7.nettyquic.tls.EncryptionLevel;
import com.protocol7.nettyquic.tls.aead.AEAD;
import io.netty.util.concurrent.Future;
import java.net.InetSocketAddress;
import java.util.Optional;

public interface Connection extends FrameSender {

  Packet sendPacket(Packet p);

  FullPacket send(Frame... frames);

  Optional<ConnectionId> getLocalConnectionId();

  Optional<ConnectionId> getRemoteConnectionId();

  Version getVersion();

  AEAD getAEAD(EncryptionLevel level);

  Future<Void> close(TransportError error, FrameType frameType, String msg);

  InetSocketAddress getPeerAddress();

  State getState();
}
