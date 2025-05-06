package com.synops.replayreader.replay.service;

import static com.synops.replayreader.common.util.Constants.JSON_DEPTH;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class ReplayReader {

  private File file;

  public ReplayReader() {
  }

  public void configure(File file) {
    this.file = file;
  }

  public List<String> read() {
    List<String> results;
    InputStream inputStream = null;
    Reader inputStreamReader = null;
    Reader buffer = null;

    try {
      inputStream = FileUtils.openInputStream(file);
      inputStreamReader = new InputStreamReader(inputStream, Charset.defaultCharset());
      buffer = new BufferedReader(inputStreamReader);
      results = getJson(buffer, JSON_DEPTH);
    } catch (IOException e) {
      System.err.println("Error reading file: " + file.getAbsolutePath());
      throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
    } finally {
      IOUtils.closeQuietly(inputStream);
      IOUtils.closeQuietly(inputStreamReader);
      IOUtils.closeQuietly(buffer);
    }
    return results;
  }

  private List<String> getJson(Reader buffer, int depth) throws IOException {
    List<String> result = new ArrayList<>(depth);

    for (int iDepth = 0; iDepth < depth; ++iDepth) {
      StringBuilder json = new StringBuilder();
      int seed = 0;

      int readChar;
      while ((readChar = buffer.read()) != -1) {
        if (readChar == 123) {
          json.append((char) readChar);
          break;
        }

        ++seed;
        if (seed >= 15) {
          result.add("-error-");
          return result;
        }
      }

      char ch;
      for (int c = 1; (readChar = buffer.read()) != -1 && c > 0; json.append(ch)) {
        ch = (char) readChar;
        if (ch == '{') {
          ++c;
        } else if (ch == '}') {
          --c;
        }
      }

      result.add(json.toString());
    }

    return result;
  }
}
