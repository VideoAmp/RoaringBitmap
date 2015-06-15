package org.roaringbitmap.bithacking;


import org.openjdk.jmh.annotations.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class UnsignedVSFlip {
    @Param({"1", "31", "65", "101", "103"})
    public short key;
    
    
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public int tointUnsignedTime() {
        return key & 0xFFFF;
    }
    
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int tointUnsignedTimeAvg() {
        return key & 0xFFFF;
    }
    
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public int flipTime() {
        return key ^ Short.MIN_VALUE;
    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int flipTimeAvg() {
        return key ^ Short.MIN_VALUE;
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public short flipTimeShort() {
        return (short) (key ^ Short.MIN_VALUE);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public short flipTimeShortAvg() {
        return (short) (key ^ Short.MIN_VALUE);
    }
    
}