/*
 * This file is part of the URI Template library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package org.weborganic.furi;

import java.util.regex.Pattern;

import org.weborganic.furi.TestUtils;
import org.weborganic.furi.TokenLiteral;


import junit.framework.TestCase;

/**
 * A test class for the <code>TokenLiteral</code>.
 * 
 * @author Christophe Lauret
 * @version 30 December 2008
 */
public class TokenLiteralTest extends TestCase {

  /**
   * Test that the constructor throws a NullPointerException for a <code>null</code> expression.
   */
  public void testNew_Null() {
    boolean nullThrown = false;
    try {
      new TokenLiteral(null);
    } catch (NullPointerException ex) {
      nullThrown = true;
    } finally {
      assertTrue(nullThrown);
    }
  }

  /**
   * Test the <code>equals</code> method.
   */
  public void testEquals() {
    TokenLiteral x = new TokenLiteral("t");
    TokenLiteral y = new TokenLiteral("t");
    TokenLiteral z = new TokenLiteral("T");
    TestUtils.satisfyEqualsContract(x, y, z);
  }

  /**
   * Test the <code>match</code> method.
   */
  public void testMatch() {
    assertMatchItsef("abc");
    assertMatchItsef("123");
    assertMatchItsef("/\\|");
    assertMatchItsef("[]{}()");
    assertMatchItsef(".,;:<>'\"!");
    assertMatchItsef("\u2014"); // m dash
    assertMatchItsef("http://pageseeder.com/user/clauret/home");
  }

  /**
   * Test the <code>pattern</code> method.
   */
  public void testPattern() {
    assertPatternIsOK("abc");
    assertPatternIsOK("123");
    assertPatternIsOK("/\\|");
    assertPatternIsOK("[]{}()");
    assertPatternIsOK(".,;:<>'\"!");
    assertPatternIsOK("\u2014"); // m dash
    assertPatternIsOK("http://pageseeder.com/user/clauret/home");
  }

  // private helpers
  // --------------------------------------------------------------------------

  /**
   * Asserts that the string of matches itself.
   */
  private void assertMatchItsef(String s) {
    TokenLiteral t = new TokenLiteral(s);
    assertTrue(t.match(s));
  }

  /**
   * Asserts that the pattern is valid and working for a given string.
   */
  private void assertPatternIsOK(String s) {
    TokenLiteral t = new TokenLiteral(s);
    Pattern p = t.pattern();
    assertNotNull(p);
    assertTrue(p.matcher(s).matches());
  }
}
