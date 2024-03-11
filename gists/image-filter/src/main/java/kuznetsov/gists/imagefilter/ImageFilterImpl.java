package kuznetsov.gists.imagefilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ImageFilterImpl implements ImageFilter {

    @Override
    public MultipartFile filterUsingBmp(MultipartFile inputFile) {
        return null;
    }

    private @NotNull String extractFormat(@NotNull MultipartFile mfile) {
        return Optional.of(mfile)
                .map(MultipartFile::getOriginalFilename)
                .filter(name -> name.contains("."))
                .map(name -> name.substring(name.lastIndexOf('.') + 1).toLowerCase())
                .orElse("");
    }

}
