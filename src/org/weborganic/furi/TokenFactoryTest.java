/*
 * This file is part of the URI Template library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package org.weborganic.furi;

import java.util.ArrayList;
import java.util.List;

import org.weborganic.furi.TokenFactory;
import org.weborganic.furi.TokenLiteral;
import org.weborganic.furi.TokenOperator;
import org.weborganic.furi.TokenOperatorD3;
import org.weborganic.furi.TokenOperatorPS;
import org.weborganic.furi.TokenVariable;
import org.weborganic.furi.Variable;
import org.weborganic.furi.VariableType;
import org.weborganic.furi.TokenFactory.Syntax;
import org.weborganic.furi.TokenOperatorD3.Operator;


import junit.framework.TestCase;

/**
 * A test class for the <code>TokenFactory</code>.
 * 
 * @author Christophe Lauret
 * @version 9 February 2009
 */
public class TokenFactoryTest extends TestCase {

  /**
   * Test that the <code>NewToken</code> method returns a <code>null</code> token for a
   * <code>null</code> expression
   */
  public void testNewToken_Null() {
    assertNull(TokenFactory.getInstance().newToken(null));
  }

  /**
   * Test that the <code>NewToken</code> method returns a <code>null</code> token for an empty
   * string.
   */
  public void testNewToken_EmptyString() {
    assertNull(TokenFactory.getInstance().newToken(""));
  }

  /**
   * Test that the <code>NewToken</code> method returns a <code>TokenLiteral</code> token
   * corresponding to the specified text.
   */
  public void testNewToken_Literal() {
    assertEquals(new TokenLiteral("x"), TokenFactory.getInstance().newToken("x"));
  }

  /**
   * Test that the <code>NewToken</code> method returns a <code>TokenVariable</code> token
   * corresponding to the specified variable definition.
   */
  public void testNewToken_Variable() {
    Variable x = new Variable("x");
    assertEquals(new TokenVariable(x), TokenFactory.getInstance().newToken("{x}"));
    Variable y = new Variable("y", "z");
    assertEquals(new TokenVariable(y), TokenFactory.getInstance().newToken("{y=z}"));
    Variable q = new Variable("q", "p", new VariableType("t"));
    assertEquals(new TokenVariable(q), TokenFactory.getInstance().newToken("{t:q=p}"));
  }

  /**
   * Test that the <code>NewToken</code> method returns a <code>TokenOperator</code> token
   * corresponding to the specified operator definition.
   */
  public void testNewToken_Operator() {
    List<Variable> vars = new ArrayList<Variable>();
    Variable y = new Variable("y");
    vars.add(y);
    // make sure that all defined operators are supported
    for (Operator o : Operator.values()) {
      TokenFactory factory = TokenFactory.getInstance(Syntax.DRAFT3);
      TokenOperator t = new TokenOperatorD3(o, "x", vars);
      assertEquals(t, factory.newToken("{-" + o.name().toLowerCase() + "|x|y}"));
    }
  }

  /**
   * Test that the <code>NewToken</code> method returns a <code>TokenOperator</code> token
   * corresponding to the specified operator definition.
   */
  public void testNewToken_Operator2() {
    List<Variable> vars = new ArrayList<Variable>();
    Variable y = new Variable("y");
    vars.add(y);
    // make sure that all defined operators are supported
    for (org.weborganic.furi.TokenOperatorPS.Operator o : org.weborganic.furi.TokenOperatorPS.Operator.values()) {
      TokenFactory factory = TokenFactory.getInstance(Syntax.PAGESEEDER);
      TokenOperatorPS t = new TokenOperatorPS(o, vars);
      assertEquals(t, factory.newToken("{" + o.character() + "y}"));
    }
  }

}
