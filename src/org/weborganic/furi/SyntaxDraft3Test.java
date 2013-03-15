/*
 * This file is part of the URI Template library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package org.weborganic.furi;

import org.weborganic.furi.Parameters;
import org.weborganic.furi.Token;
import org.weborganic.furi.TokenOperatorD3;
import org.weborganic.furi.TokenVariable;
import org.weborganic.furi.URIParameters;
import org.weborganic.furi.Variable;

import junit.framework.TestCase;

/**
 * A test class for the Syntax defined <code>DRAFT3</code> and mostly implemented 
 * in <code>TokenOperatorD3</code>.
 * 
 * <p>
 * This class uses the examples defined in the operators specifications.
 * 
 * @see <a
 *      href="http://bitworking.org/projects/URI-Templates/spec/draft-gregorio-uritemplate-03.html#evaluating">URI
 *      Template (Draft 3) - URI Template substitution </a>
 * 
 * @author Christophe Lauret
 * @version 30 December 2008
 */
public class SyntaxDraft3Test extends TestCase {

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
   * Test the OPT operator expand method.
   */
  public void testExpand_Opt() {
    // setup
    Parameters params = new URIParameters();
    params.set("foo", new String[] { "fred" });
    // test
    assertExpandOK("{-opt|fred@example.org|foo}", "fred@example.org", params);
    assertExpandOK("{-opt|fred@example.org|bar}", "", params);
  }

  /**
   * Test the NEG operator expand method.
   */
  public void testExpand_Neg() {
    // setup
    Parameters params = new URIParameters();
    params.set("foo", new String[] { "fred" });
    // test
    assertExpandOK("{-neg|fred@example.org|foo}", "", params);
    assertExpandOK("{-neg|fred@example.org|bar}", "fred@example.org", params);
  }

  /**
   * Test the PREFIX operator expand method.
   */
  public void testExpand_Prefix() {
    // setup
    Parameters params = new URIParameters();
    params.set("foo", new String[] { "fred" });
    params.set("bar", new String[] { "fee", "fi", "fo", "fum" });
    params.set("baz", new String[] {});
    // test
    assertExpandOK("{-prefix|/|foo}", "/fred", params);
    assertExpandOK("{-prefix|/|bar}", "/fee/fi/fo/fum", params);
    assertExpandOK("{-prefix|/|baz}", "", params);
    assertExpandOK("{-prefix|/|qux}", "", params);
  }

  /**
   * Test the SUFFIX operator expand method.
   */
  public void testExpand_Suffix() {
    // setup
    Parameters params = new URIParameters();
    params.set("foo", new String[] { "fred" });
    params.set("bar", new String[] { "fee", "fi", "fo", "fum" });
    params.set("baz", new String[] {});
    // test
    assertExpandOK("{-suffix|/|foo}", "fred/", params);
    assertExpandOK("{-suffix|/|bar}", "fee/fi/fo/fum/", params);
    assertExpandOK("{-suffix|/|baz}", "", params);
    assertExpandOK("{-suffix|/|qux}", "", params);
  }

  /**
   * Test the JOIN operator expand method.
   */
  public void testExpand_Join() {
    // setup
    Parameters params = new URIParameters();
    params.set("foo", "fred");
    params.set("bar", "barney");
    params.set("baz", "");
    // test
    assertExpandOK("{-join|&|foo,bar,baz,qux}", "foo=fred&bar=barney&baz=", params);
    assertExpandOK("{-join|&|bar}", "bar=barney", params);
    assertExpandOK("{-join|&|qux}", "", params);
  }

  /**
   * Test the LIST operator expand method.
   */
  public void testExpand_List() {
    // setup
    Parameters params = new URIParameters();
    params.set("foo", new String[] { "fred", "barney", "wilma" });
    params.set("bar", new String[] { "a", "", "c" });
    params.set("baz", new String[] { "betty" });
    params.set("qux", new String[] {});
    // test
    assertExpandOK("{-list|/|foo}", "fred/barney/wilma", params);
    assertExpandOK("{-list|/|bar}", "a//c", params);
    assertExpandOK("{-list|/|baz}", "betty", params);
    assertExpandOK("{-list|/|qux}", "", params);
    assertExpandOK("{-list|/|corge}", "", params);
  }

  // private helpers
  // --------------------------------------------------------------------------

  /**
   * Asserts that given expansion is expanded correctly given a set of parameters.
   * 
   * @param expansion The expression to expand.
   * @param value The expected value (expanded form).
   * @param parameters The parameters to use for expansion.
   */
  private void assertExpandOK(String expansion, String value, Parameters parameters) {
    Token t = TokenOperatorD3.parse(expansion);
    assertEquals(value, t.expand(parameters));
  }
}
