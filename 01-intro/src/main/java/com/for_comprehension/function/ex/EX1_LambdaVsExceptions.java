package com.for_comprehension.function.ex;

import java.util.stream.Stream;

class EX1_LambdaVsExceptions {

    public static void main(String[] args) {
        /*Stream.of(1,2,3)
          .map(i -> {
              try {
                  return process(i);
              } catch (Exception e) {
                  throw new RuntimeException(e);
              }
          })
          .map(i -> process(i))
          .forEach(System.out::println);*/
    }

    public static int process(int i) throws Exception {
        return i;
    }
}
