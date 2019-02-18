package com.protocol7.nettyquic.tls.aead;

import static java.util.Objects.requireNonNull;

import com.protocol7.nettyquic.tls.EncryptionLevel;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AEADs {

  private final Logger log = LoggerFactory.getLogger(AEADs.class);

  private final AEAD initialAead;
  private final AtomicReference<AEAD> handshakeAead = new AtomicReference<>();
  private final AtomicReference<AEAD> oneRttAead = new AtomicReference<>();

  public AEADs(final AEAD initialAead) {
    this.initialAead = requireNonNull(initialAead);
  }

  public boolean available(final EncryptionLevel level) {
    if (level == EncryptionLevel.Initial) {
      return true;
    } else if (level == EncryptionLevel.Handshake) {
      return handshakeAead.get() != null;
    } else {
      return oneRttAead.get() != null;
    }
  }

  public AEAD get(final EncryptionLevel level) {
    requireNonNull(level);

    if (level == EncryptionLevel.Initial) {
      log.debug("Using initial AEAD: {}", initialAead);
      return initialAead;
    } else if (level == EncryptionLevel.Handshake) {
      final AEAD aead = handshakeAead.get();
      if (aead == null) {
        throw new IllegalStateException("Handshake AEAD not set");
      }

      log.debug("Using handshake AEAD: {}", aead);
      return aead;
    } else {
      final AEAD aead = oneRttAead.get();
      if (aead == null) {
        throw new IllegalStateException("1-RTT AEAD not set");
      }

      log.debug("Using 1-RTT AEAD: {}", aead);
      return aead;
    }
  }

  public void setHandshakeAead(final AEAD handshakeAead) {
    this.handshakeAead.set(requireNonNull(handshakeAead));
  }

  public void setOneRttAead(final AEAD oneRttAead) {
    this.oneRttAead.set(requireNonNull(oneRttAead));
  }
}
