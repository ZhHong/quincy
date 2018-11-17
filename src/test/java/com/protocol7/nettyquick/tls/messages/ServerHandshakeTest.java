package com.protocol7.nettyquick.tls.messages;

import com.protocol7.nettyquick.tls.extensions.TransportParameters;
import com.protocol7.nettyquick.tls.messages.ServerHandshake;
import com.protocol7.nettyquick.utils.Hex;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static com.protocol7.nettyquick.utils.Hex.hex;
import static org.junit.Assert.assertEquals;

public class ServerHandshakeTest {

    @Test
    public void parseKnown() {
        byte[] x = Hex.dehex("0800006500630ff5005f0000006508cacaca1a0000006500500000000400008000000a000400008000000b000400008000000100040000c00000020002006400080002006400030002001e0005000205ac00090000000600102a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a0b000ab400000ab00006103082060c308204f4a00302010202120331734073d84f95676eaf0d2cc28d74700d300d06092a864886f70d01010b0500304a310b300906035504061302555331163014060355040a130d4c6574277320456e6372797074312330210603550403131a4c6574277320456e637279707420417574686f72697479205833301e170d3138303930383038323031315a170d3138313230373038323031315a301b3119301706035504031310717569632e636c656d656e74652e696f30820122300d06092a864886f70d01010105000382010f003082010a0282010100b9e2bc48d86f7d18bb3b54a9c86ca9e5f858d0c638f9e9ed35756ea613edc94a8b811b72bef613ba744c6f652e3352a2f48718bddc7e3cd161ad227f1b21856971966f920cc68bca2ddc7ab64973816f79c14c28fb63e0c1e28257d6b3cdaa7d18552f0b89dc5428886c14e808a63ae15ec6b5ba023789ae5d8cf2f141851cd6bff8f1f9548d3632f470c3f9515420d3ed549cd7273dffee588f7606e14b2e14ce97b972ee1c9203538a443a0aec467f7fbccbd04fda734e29ed67237b9b7c2fa08f8a146ce6a8600fed553709c498a6cbefe623db31fcabbd971cc2fc3759c2fecf44cdd0d01b04dffd88ec3451304349a1fe3341f7bf5ef0fd599d3df807630203010001a382031930820315300e0603551d0f0101ff0404030205a0301d0603551d250416301406082b0601050507030106082b06010505070302300c0603551d130101ff04023000301d0603551d0e04160414ebe3f525128263febab2ee7ce97772c933c40c67301f0603551d23041830168014a84a6a63047dddbae6d139b7a64565eff3a8eca1306f06082b0601050507010104633061302e06082b060105050730018622687474703a2f2f6f6373702e696e742d78332e6c657473656e63727970742e6f7267302f06082b060105050730028623687474703a2f2f636572742e696e742d78332e6c657473656e63727970742e6f72672f301b0603551d11041430128210717569632e636c656d656e74652e696f3081fe0603551d200481f63081f33008060667810c0102013081e6060b2b0601040182df130101013081d6302606082b06010505070201161a687474703a2f2f6370732e6c657473656e63727970742e6f72673081ab06082b0601050507020230819e0c819b54686973204365727469666963617465206d6179206f6e6c792062652072656c6965642075706f6e2062792052656c79696e67205061727469657320616e64206f6e6c7920696e206163636f7264616e636520776974682074686520436572746966696361746520506f6c69637920666f756e642061742068747470733a2f2f6c657473656e63727970742e6f72672f7265706f7369746f72792f30820105060a2b06010401d6790204020481f60481f300f1007600db74afeecb29ecb1feca3e716d2ce5b9aabb36f7847183c75d9d4f37b61fbf6400000165b87b471c000004030047304502210093ec94a29749d6e2063ac08c2c4a1fa81609282ab5a94ea511de034a687943c5022033e08df586e894492ed36b54271ac90a4b80d9450f1d4cc7f95692939b56e8a0007700293c519654c83965baaa50fc5807d4b76fbf587a2972dca4c30cf4e54547f47800000165b87b480c0000040300483046022100be0296f3e3690580583d282555a419fc6670c3f408b73a6c563df4e46a01f3b7022100c40db8c77de14a268ffb0b6d293a16b594445565a7ba5cb9516552fe50c3fcfa300d06092a864886f70d01010b05000382010100799b8ee9b42c981bb689057139d4a646dbbf557b18662f9fcad793a1236d7b1598bba48051f7b976be4c1c3da6dce794a182311de2ad48c8e213ba3b05551466055a0978e34ff9d1d62c89bd5c40b7d7a477895c8e3a2e768c501e6b3be8a53d75c12bf0cff3e3f3e1b81f379efa49078d26809967e3b061bca280785b5f0fe73e811e0be4d1b42c2e87ec96dcf0fc9158f1337351c27c76879245602d1963824cbc865f37fae47de6582348f298c91cbca82c0bff59ad85d686a5f3fabbe683f6db038cc1448fe2eaed10de899901a19eae667aa315c7e0e2c2e5c2ebff685bdb01b90f6b6e52a708b374a44b15536b5ad4f655b845873f3aad7a653609c9130000000496308204923082037aa00302010202100a0141420000015385736a0b85eca708300d06092a864886f70d01010b0500303f31243022060355040a131b4469676974616c205369676e617475726520547275737420436f2e311730150603550403130e44535420526f6f74204341205833301e170d3136303331373136343034365a170d3231303331373136343034365a304a310b300906035504061302555331163014060355040a130d4c6574277320456e6372797074312330210603550403131a4c6574277320456e637279707420417574686f7269747920583330820122300d06092a864886f70d01010105000382010f003082010a02820101009cd30cf05ae52e47b7725d3783b3686330ead735261925e1bdbe35f170922fb7b84b4105aba99e350858ecb12ac468870ba3e375e4e6f3a76271ba7981601fd7919a9ff3d0786771c8690e9591cffee699e9603c48cc7eca4d7712249d471b5aebb9ec1e37001c9cac7ba705eace4aebbd41e53698b9cbfd6d3c9668df232a42900c867467c87fa59ab8526114133f65e98287cbdbfa0e56f68689f3853f9786afb0dc1aef6b0d95167dc42ba065b299043675806bac4af31b9049782fa2964f2a20252904c674c0d031cd8f31389516baa833b843f1b11fc3307fa27931133d2d36f8e3fcf2336ab93931c5afc48d0d1d641633aafa8429b6d40bc0d87dc3930203010001a382017d3082017930120603551d130101ff040830060101ff020100300e0603551d0f0101ff040403020186307f06082b0601050507010104733071303206082b060105050730018626687474703a2f2f697372672e747275737469642e6f6373702e6964656e74727573742e636f6d303b06082b06010505073002862f687474703a2f2f617070732e6964656e74727573742e636f6d2f726f6f74732f647374726f6f74636178332e703763301f0603551d23041830168014c4a7b1a47b2c71fadbe14b9075ffc4156085891030540603551d20044d304b3008060667810c010201303f060b2b0601040182df130101013030302e06082b060105050702011622687474703a2f2f6370732e726f6f742d78312e6c657473656e63727970742e6f7267303c0603551d1f043530333031a02fa02d862b687474703a2f2f63726c2e6964656e74727573742e636f6d2f445354524f4f544341583343524c2e63726c301d0603551d0e04160414a84a6a63047dddbae6d139b7a64565eff3a8eca1300d06092a864886f70d01010b05000382010100dd33d711f3635838dd1815fb0955be7656b97048a56947277bc2240892f15a1f4a1229372474511c6268b8cd957067e5f7a4bc4e2851cd9be8ae879dead8ba5aa1019adcf0dd6a1d6ad83e57239ea61e04629affd705cab71f3fc00a48bc94b0b66562e0c154e5a32aad20c4e9e6bbdcc8f6b5c332a398cc77a8e67965072bcb28fe3a165281ce520c2e5f83e8d50633fb776cce40ea329e1f925c41c1746c5b5d0a5f33cc4d9fac38f02f7b2c629dd9a3916f251b2f90b119463df67e1ba67a87b9a37a6d18fa25a5918715e0f2162f58b0062f2c6826c64b98cdda9f0cf97f90ed434a12444e6f737a28eaa4aa6e7b4c7d87dde0c90244a787afc3345bb44200000f000104080401003ba292498f188921651a6dcfe03416ff136070fbd84cb4d442fa9c389fb2cbfaa4e5ecff4bf644b93334faebc3b5732568ca965609ac32a37587ec4540f6617e9a5ee651b7e67bd4e46be7275813a1b1efdb5d5b072903ba0358e41e9fcdab52ae1b569cc5d82b00b70d30410d4ed382b978a41f01e5d6ee484edd34dc2d771c0bd35db8244ed6d024909cbf07d9864e38cadfe77a7172b8add527fa86b1f98d067b0803b8fabd8ae6ef957e52762d59dfb95c9c53cef5e4af4ba7b97974912edd37a84024f23ddac11e82cedd29f1d128137d1ae947f832cc4108fbc035f405ab93e48107db69795d26c6a1ec4e576b374b554b1a7877b5b059f5b6dd38293a140000204c6e3380e3b4034484753f79b0946ffc8a201fb4d3c1e8031a815ede45d9dbed");
        ByteBuf bb = Unpooled.wrappedBuffer(x);

        ServerHandshake handshake = ServerHandshake.parse(bb);

        assertEquals(1, handshake.getExtensions().size());
        TransportParameters tps = (TransportParameters) handshake.getExtensions().get(0);

        assertEquals(-1, tps.getAckDelayExponent());
        assertEquals(true, tps.isDisableMigration());
        assertEquals(30, tps.getIdleTimeout());
        assertEquals(100, tps.getInitialMaxBidiStreams());
        assertEquals(49152, tps.getInitialMaxData());
        assertEquals(32768, tps.getInitialMaxStreamDataBidiLocal());
        assertEquals(32768, tps.getInitialMaxStreamDataBidiRemote());
        assertEquals(32768, tps.getInitialMaxStreamDataUni());
        assertEquals(100, tps.getInitialMaxUniStreams());
        assertEquals(-1, tps.getMaxAckDelay());
        assertEquals(1452, tps.getMaxPacketSize());
        assertEquals(16, tps.getStatelessResetToken().length);
        assertEquals(0, tps.getOriginalConnectionId().length);

        assertEquals(2, handshake.getServerCertificates().size());
        assertEquals("3082060c308204f4a00302010202120331734073d84f95676eaf0d2cc28d74700d300d06092a864886f70d01010b0500304a310b300906035504061302555331163014060355040a130d4c6574277320456e6372797074312330210603550403131a4c6574277320456e637279707420417574686f72697479205833301e170d3138303930383038323031315a170d3138313230373038323031315a301b3119301706035504031310717569632e636c656d656e74652e696f30820122300d06092a864886f70d01010105000382010f003082010a0282010100b9e2bc48d86f7d18bb3b54a9c86ca9e5f858d0c638f9e9ed35756ea613edc94a8b811b72bef613ba744c6f652e3352a2f48718bddc7e3cd161ad227f1b21856971966f920cc68bca2ddc7ab64973816f79c14c28fb63e0c1e28257d6b3cdaa7d18552f0b89dc5428886c14e808a63ae15ec6b5ba023789ae5d8cf2f141851cd6bff8f1f9548d3632f470c3f9515420d3ed549cd7273dffee588f7606e14b2e14ce97b972ee1c9203538a443a0aec467f7fbccbd04fda734e29ed67237b9b7c2fa08f8a146ce6a8600fed553709c498a6cbefe623db31fcabbd971cc2fc3759c2fecf44cdd0d01b04dffd88ec3451304349a1fe3341f7bf5ef0fd599d3df807630203010001a382031930820315300e0603551d0f0101ff0404030205a0301d0603551d250416301406082b0601050507030106082b06010505070302300c0603551d130101ff04023000301d0603551d0e04160414ebe3f525128263febab2ee7ce97772c933c40c67301f0603551d23041830168014a84a6a63047dddbae6d139b7a64565eff3a8eca1306f06082b0601050507010104633061302e06082b060105050730018622687474703a2f2f6f6373702e696e742d78332e6c657473656e63727970742e6f7267302f06082b060105050730028623687474703a2f2f636572742e696e742d78332e6c657473656e63727970742e6f72672f301b0603551d11041430128210717569632e636c656d656e74652e696f3081fe0603551d200481f63081f33008060667810c0102013081e6060b2b0601040182df130101013081d6302606082b06010505070201161a687474703a2f2f6370732e6c657473656e63727970742e6f72673081ab06082b0601050507020230819e0c819b54686973204365727469666963617465206d6179206f6e6c792062652072656c6965642075706f6e2062792052656c79696e67205061727469657320616e64206f6e6c7920696e206163636f7264616e636520776974682074686520436572746966696361746520506f6c69637920666f756e642061742068747470733a2f2f6c657473656e63727970742e6f72672f7265706f7369746f72792f30820105060a2b06010401d6790204020481f60481f300f1007600db74afeecb29ecb1feca3e716d2ce5b9aabb36f7847183c75d9d4f37b61fbf6400000165b87b471c000004030047304502210093ec94a29749d6e2063ac08c2c4a1fa81609282ab5a94ea511de034a687943c5022033e08df586e894492ed36b54271ac90a4b80d9450f1d4cc7f95692939b56e8a0007700293c519654c83965baaa50fc5807d4b76fbf587a2972dca4c30cf4e54547f47800000165b87b480c0000040300483046022100be0296f3e3690580583d282555a419fc6670c3f408b73a6c563df4e46a01f3b7022100c40db8c77de14a268ffb0b6d293a16b594445565a7ba5cb9516552fe50c3fcfa300d06092a864886f70d01010b05000382010100799b8ee9b42c981bb689057139d4a646dbbf557b18662f9fcad793a1236d7b1598bba48051f7b976be4c1c3da6dce794a182311de2ad48c8e213ba3b05551466055a0978e34ff9d1d62c89bd5c40b7d7a477895c8e3a2e768c501e6b3be8a53d75c12bf0cff3e3f3e1b81f379efa49078d26809967e3b061bca280785b5f0fe73e811e0be4d1b42c2e87ec96dcf0fc9158f1337351c27c76879245602d1963824cbc865f37fae47de6582348f298c91cbca82c0bff59ad85d686a5f3fabbe683f6db038cc1448fe2eaed10de899901a19eae667aa315c7e0e2c2e5c2ebff685bdb01b90f6b6e52a708b374a44b15536b5ad4f655b845873f3aad7a653609c913", hex(handshake.getServerCertificates().get(0)));
        assertEquals("308204923082037aa00302010202100a0141420000015385736a0b85eca708300d06092a864886f70d01010b0500303f31243022060355040a131b4469676974616c205369676e617475726520547275737420436f2e311730150603550403130e44535420526f6f74204341205833301e170d3136303331373136343034365a170d3231303331373136343034365a304a310b300906035504061302555331163014060355040a130d4c6574277320456e6372797074312330210603550403131a4c6574277320456e637279707420417574686f7269747920583330820122300d06092a864886f70d01010105000382010f003082010a02820101009cd30cf05ae52e47b7725d3783b3686330ead735261925e1bdbe35f170922fb7b84b4105aba99e350858ecb12ac468870ba3e375e4e6f3a76271ba7981601fd7919a9ff3d0786771c8690e9591cffee699e9603c48cc7eca4d7712249d471b5aebb9ec1e37001c9cac7ba705eace4aebbd41e53698b9cbfd6d3c9668df232a42900c867467c87fa59ab8526114133f65e98287cbdbfa0e56f68689f3853f9786afb0dc1aef6b0d95167dc42ba065b299043675806bac4af31b9049782fa2964f2a20252904c674c0d031cd8f31389516baa833b843f1b11fc3307fa27931133d2d36f8e3fcf2336ab93931c5afc48d0d1d641633aafa8429b6d40bc0d87dc3930203010001a382017d3082017930120603551d130101ff040830060101ff020100300e0603551d0f0101ff040403020186307f06082b0601050507010104733071303206082b060105050730018626687474703a2f2f697372672e747275737469642e6f6373702e6964656e74727573742e636f6d303b06082b06010505073002862f687474703a2f2f617070732e6964656e74727573742e636f6d2f726f6f74732f647374726f6f74636178332e703763301f0603551d23041830168014c4a7b1a47b2c71fadbe14b9075ffc4156085891030540603551d20044d304b3008060667810c010201303f060b2b0601040182df130101013030302e06082b060105050702011622687474703a2f2f6370732e726f6f742d78312e6c657473656e63727970742e6f7267303c0603551d1f043530333031a02fa02d862b687474703a2f2f63726c2e6964656e74727573742e636f6d2f445354524f4f544341583343524c2e63726c301d0603551d0e04160414a84a6a63047dddbae6d139b7a64565eff3a8eca1300d06092a864886f70d01010b05000382010100dd33d711f3635838dd1815fb0955be7656b97048a56947277bc2240892f15a1f4a1229372474511c6268b8cd957067e5f7a4bc4e2851cd9be8ae879dead8ba5aa1019adcf0dd6a1d6ad83e57239ea61e04629affd705cab71f3fc00a48bc94b0b66562e0c154e5a32aad20c4e9e6bbdcc8f6b5c332a398cc77a8e67965072bcb28fe3a165281ce520c2e5f83e8d50633fb776cce40ea329e1f925c41c1746c5b5d0a5f33cc4d9fac38f02f7b2c629dd9a3916f251b2f90b119463df67e1ba67a87b9a37a6d18fa25a5918715e0f2162f58b0062f2c6826c64b98cdda9f0cf97f90ed434a12444e6f737a28eaa4aa6e7b4c7d87dde0c90244a787afc3345bb442", hex(handshake.getServerCertificates().get(1)));

        assertEquals(2052, handshake.getVerificationSignatureType());
        assertEquals("3ba292498f188921651a6dcfe03416ff136070fbd84cb4d442fa9c389fb2cbfaa4e5ecff4bf644b93334faebc3b5732568ca965609ac32a37587ec4540f6617e9a5ee651b7e67bd4e46be7275813a1b1efdb5d5b072903ba0358e41e9fcdab52ae1b569cc5d82b00b70d30410d4ed382b978a41f01e5d6ee484edd34dc2d771c0bd35db8244ed6d024909cbf07d9864e38cadfe77a7172b8add527fa86b1f98d067b0803b8fabd8ae6ef957e52762d59dfb95c9c53cef5e4af4ba7b97974912edd37a84024f23ddac11e82cedd29f1d128137d1ae947f832cc4108fbc035f405ab93e48107db69795d26c6a1ec4e576b374b554b1a7877b5b059f5b6dd38293a", hex(handshake.getVerificationSignature()));
        assertEquals("4c6e3380e3b4034484753f79b0946ffc8a201fb4d3c1e8031a815ede45d9dbed", hex(handshake.getVerificationData()));
    }
}