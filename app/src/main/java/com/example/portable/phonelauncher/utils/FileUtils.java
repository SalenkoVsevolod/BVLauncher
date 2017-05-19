package com.example.portable.phonelauncher.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Salenko Vsevolod on 19.05.2017.
 */

public class FileUtils {
    public static List<File> getFilesFromFolder(File folder) {
        return Arrays.asList(folder.listFiles());
    }
}
