package kuznetsov.gists.imagefilter;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MultipartFileImpl implements MultipartFile {

    private final String name;
    private final String filename;
    private final String format;
    private final byte[] bytes;

    public MultipartFileImpl(byte @NotNull [] bytes, @NotNull String format, @NotNull String filename, @NotNull String name) {
        this.name = name;
        this.filename = filename;
        this.format = format;
        this.bytes = bytes;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        return format;
    }

    @Override
    public boolean isEmpty() {
        return bytes.length == 0;
    }

    @Override
    public long getSize() {
        return bytes.length;
    }

    @Override
    public byte @NotNull [] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public @NotNull InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(bytes);
    }

    @Override
    public void transferTo(@NotNull File destination) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            fos.write(bytes);
        }
    }

}
