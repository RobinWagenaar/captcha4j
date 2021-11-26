/*
 * Copyright 2021 Subhajit Das
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.captcha4j.core.util;

import com.github.captcha4j.core.audio.Sample;

import java.io.*;

/**
 * File handling utility
 *
 * @author <a href="mailto:subhajitdas298@gmail.com">Subhajit Das</a>
 */
public class FileUtil {

    /**
     * Get a file resource and return it as an InputStream. Intended primarily
     * to read in binary files which are contained in a jar.
     *
     * @param filename the file to read
     * @return An @{link InputStream} to the file
     */
    public static InputStream readResource(String filename) {
        InputStream inputStream = FileUtil.class.getResourceAsStream(filename);
        if (inputStream == null) {
            throw new RuntimeException(new FileNotFoundException("File '"
                    + filename + "' not found."));
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        byte[] data = new byte[16384];
        int nRead;

        try {
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(buffer.toByteArray());
    }

    /**
     * Reads a file in to audio sample
     *
     * @param filename the file to read
     * @return the audio sample
     */
    public static Sample readSample(String filename) {
        InputStream is = readResource(filename);
        return new Sample(is);
    }

}
