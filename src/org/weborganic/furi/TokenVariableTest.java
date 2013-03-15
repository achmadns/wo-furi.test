/*
 * This file is part of the URI Template library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package org.weborganic.furi;

import org.weborganic.furi.TestUtils;
import org.weborganic.furi.TokenVariable;
import org.weborganic.furi.Variable;


import junit.framework.TestCase;

/**
 * A test class for the <code>TokenVariable</code>.
 * 
 * @author Christophe Lauret
 * @version 30 December 2008
 */
public class TokenVariableTest extends TestCase {

  /**
   * Test that the constructor throws a NullPointerException for a <code>null</code> expression.
   */
  public void testNew_Null() {
    boolean nullThrown = false;
    try {
      new TokenVariable((Variable) null);
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
    Variable v = new Variable("v");
    Variable w = new Variable("w");
    TokenVariable x = new TokenVariable(v);
    TokenVariable y = new TokenVariable(v);
    TokenVariable z = new TokenVariable(w);
    TestUtils.satisfyEqualsContract(x, y, z);
  }

  /**
   * Test the <code>match</code> method.
   */
  public void testMatch() {
    TokenVariable v = new TokenVariable("X");
    // should match unreserved characters
    assertTrue(v.match("abcxyz"));
    assertTrue(v.match("ABCXYZ"));
    assertTrue(v.match("0123456789"));
    assertTrue(v.match("_"));
    assertTrue(v.match("-"));
    assertTrue(v.match("."));
    assertTrue(v.match("%45"));
    // should not match reserved characters in ASCII range
    assertFalse(v.match("%"));
    assertFalse(v.match("/"));
    assertFalse(v.match("*"));
    assertFalse(v.match("*"));
    // should not match reserved characters outside ASCII range 
    assertFalse(v.match("\u00e9"));
  }
}
