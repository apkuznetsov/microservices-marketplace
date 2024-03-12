package kuznetsov.gists.imagefilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageFilterImpl implements ImageFilter {

    @Override
    public MultipartFile filterUsingBmp(MultipartFile inputFile) {
        return null;
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
