/*
 * (c) the authors Licensed under the Apache License, Version 2.0.
 */

package org.roaringbitmap.buffer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

public class TestExamples {
  @Test
  public void serializationExample() throws IOException {
    File tmpfile = File.createTempFile("roaring", "bin");
    tmpfile.deleteOnExit();
    FileChannel fc = FileChannel.open(tmpfile.toPath(), StandardOpenOption.WRITE, StandardOpenOption.READ);
    MutableRoaringBitmap bitmap = MutableRoaringBitmap.bitmapOf(0, 2, 55, 64, 1 << 30);
    System.out.println("Created the bitmap " + bitmap);
    // If there were runs of consecutive values, you could
    // call Bitmap.runOptimize(); to improve compression
    ByteBuffer bb = ByteBuffer.allocate(bitmap.serializedSizeInBytes());
    bitmap.serialize(bb);
    bb.flip();
    fc.write(bb);
    fc.force(false);
    long totalcount = fc.position();
    System.out.println("Serialized total count = " + totalcount + " bytes");
    fc.close();
    RandomAccessFile memoryMappedFile = new RandomAccessFile(tmpfile, "r");
    ByteBuffer bb1 = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, totalcount);
    ImmutableRoaringBitmap mapped = new ImmutableRoaringBitmap(bb1);
    System.out.println("Mapped the bitmap " + mapped);
    memoryMappedFile.close();
    if (!mapped.equals(bitmap)) {
      throw new RuntimeException("This will not happen");
    }
  }
}
