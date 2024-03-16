package kuznetsov.gists.imagefilter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageFilterImpl implements ImageFilter {

    private final long JPEG_THRESHOLD_SIZE = 3_000_000L;
    private final float JPEG_COMPRESSION_QUALITY = 0.75f;

    @Override
    @SneakyThrows
    public @NotNull MultipartFile filterUsingBmp(@NotNull MultipartFile mfile) {
        String format = extractFormat(mfile);
        long size = mfile.getSize();
        boolean isJpegForCompression = ("jpg".equalsIgnoreCase(format) || "jpeg".equalsIgnoreCase(format))
                && (size >= JPEG_THRESHOLD_SIZE);

        byte[] bmpBytes = toBmp(mfile.getInputStream()).toByteArray();
        byte[] outBytes = isJpegForCompression
                ? toCompressedJpegFromBmpBytes(bmpBytes, format)
                : toSpecifiedFormatFromBmpBytes(bmpBytes, format);

        return new MultipartFileImpl(outBytes, format, mfile.getOriginalFilename(), mfile.getName());
    }

    private byte @NotNull [] toCompressedJpegFromBmpBytes(byte @NotNull [] bmpBytes, @NotNull String format) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bmpBytes);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
            ImageWriter writer = writers.next();

            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionQuality(JPEG_COMPRESSION_QUALITY);

            try (MemoryCacheImageOutputStream mcios = new MemoryCacheImageOutputStream(baos)) {
                BufferedImage readImage = ImageIO.read(bais);
                writer.setOutput(mcios);
                writer.write(null, new IIOImage(readImage, null, null), writeParam);
            }

            writer.dispose();
            return baos.toByteArray();
        }
    }

    private byte @NotNull [] toSpecifiedFormatFromBmpBytes(byte @NotNull [] bmpBytes, @NotNull String format) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bmpBytes);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            BufferedImage readImage = ImageIO.read(bais);
            ImageIO.write(readImage, format, baos);

            return baos.toByteArray();
        }
    }

    private @NotNull ByteArrayOutputStream toBmp(@NotNull InputStream inStream) throws IOException {
        BufferedImage readImage = ImageIO.read(inStream);
        BufferedImage newImage = new BufferedImage(
                readImage.getWidth(),
                readImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g = newImage.createGraphics();
        g.drawImage(readImage, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ImageIO.write(newImage, "bmp", outStream);
        return outStream;
    }

    private @NotNull String extractFormat(@NotNull MultipartFile mfile) {
        return Optional.of(mfile)
                .map(MultipartFile::getOriginalFilename)
                .filter(name -> name.contains("."))
                .map(name -> name.substring(name.lastIndexOf('.') + 1).toLowerCase())
                .orElse("");
    }

}
