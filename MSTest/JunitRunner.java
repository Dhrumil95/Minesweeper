//package com.javacodegeeks.junit;
/**
 * Minesweeper Testing
 * Dhrumil Patel, dpate85@uic.edu
 * Yue Yu, yyu31@uic.edu
**/

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JunitRunner {

 public static void main(String[] args) {

  Result result = JUnitCore.runClasses( MSTest.class );
  for (Failure fail : result.getFailures()) {
   System.out.println(fail.toString());
  }
  if (result.wasSuccessful()) {
   System.out.println("All tests finished successfully...");
  }
 }
}
