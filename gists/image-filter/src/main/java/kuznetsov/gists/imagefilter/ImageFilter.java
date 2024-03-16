package kuznetsov.gists.imagefilter;

import org.springframework.web.multipart.MultipartFile;

public interface ImageFilter {

    MultipartFile filterUsingBmp(MultipartFile inputFile);

}
