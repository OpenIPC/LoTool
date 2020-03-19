package org.openipc.lotool;

import com.hisilicon.fastboot.utilitily.AESUtils;
import org.soyatec.tools.register.crypt.EncryptAndDecrypt;
import org.soyatec.tools.register.uboot.WorkUtils;

import java.nio.file.*;

@FunctionalInterface
interface Decryptor {
    void decrypt(Path inputPath, Path outputPath) throws Exception;
}

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: LoTool <HiTool directory>");
            System.exit(1);
        }

        Path basePath = Paths.get(args[0]);

        decryptAll(
                basePath.resolve("Resources/HiReg/ChipHome/en"),
                "*.chip",
                ".xml",
                (inputPath, outputPath) -> {
                    EncryptAndDecrypt.decryptFile(inputPath.toFile(), outputPath.toFile());
                }
        );

        decryptAll(
                basePath.resolve("Resources/HiReg/ChipHome/zh"),
                "*.chip",
                ".xml",
                (inputPath, outputPath) -> {
                    EncryptAndDecrypt.decryptFile(inputPath.toFile(), outputPath.toFile());
                }
        );

        decryptAll(
                basePath.resolve("Resources/Common/ChipProperties"),
                "*.chip",
                ".txt",
                (inputPath, outputPath) -> {
                    Files.write(outputPath, AESUtils.decrypt(Files.readAllBytes(inputPath), "HiReg-5D765B15-8F5B-46DC-9B7C-80322B8F74E4"));
                }
        );

        decryptAll(
                basePath.resolve("Resources/Common/ChipFrameSettingConstants"),
                "*.fsc",
                ".txt",
                (inputPath, outputPath) -> {
                    Files.write(outputPath, WorkUtils.decrypt(Files.readAllBytes(inputPath)));
                }
        );

    }

    private static void decryptAll(Path basePath, String glob, String ext, Decryptor decryptor) throws Exception {
        DirectoryStream<Path> paths = Files.newDirectoryStream(basePath, glob);
        for (Path inputPath : paths) {
            Path outputPath = inputPath.resolveSibling(inputPath.getFileName() + ext);
            decryptor.decrypt(inputPath, outputPath);
        }
    }

}
