/*
 * This file is part of the URI Template library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package org.weborganic.furi;

import org.weborganic.furi.TestUtils;
import org.weborganic.furi.URIPattern;
import org.weborganic.furi.URITemplate;


import junit.framework.TestCase;

/**
 * Test class for the <code>URIPattern</code>.
 * 
 * @author Christophe Lauret
 * @version 30 December 2008
 */
public class URIPatternTest extends TestCase {

  /**
   * Test that the constructor throws a NullPointerException for a <code>null</code> expression.
   */
  public void testNew_NullString() {
    boolean nullThrown = false;
    try {
      new URIPattern((String) null);
    } catch (NullPointerException ex) {
      nullThrown = true;
    } finally {
      assertTrue(nullThrown);
    }
  }

  /**
   * Test that the constructor throws a NullPointerException for a <code>null</code> template.
   */
  public void testNew_NullTemplate() {
    boolean nullThrown = false;
    try {
      new URIPattern((URITemplate) null);
    } catch (NullPointerException ex) {
      nullThrown = true;
    } finally {
      assertTrue(nullThrown);
    }
  }

  /**
   * Test that the constructor handles an empty string.
   */
  public void testNew_EmptyString() {
    new URIPattern("");
  }

  /**
   * Test the <code>equals</code> method.
   */
  public void testEquals_Contract() {
    URIPattern x = new URIPattern("http://acme.com/{X}");
    URIPattern y = new URIPattern("http://acme.com/{X}");
    URIPattern z = new URIPattern("http://acme.com/{Y}");
    TestUtils.satisfyEqualsContract(x, y, z);
  }

  /**
	 * Test the <code>match</code> method 
	 */
  public void testMatchSingle() {
    URIPattern x = new URIPattern("http://acme.com/{X}");
    assertTrue(x.match("http://acme.com/toast"));
    assertTrue(x.match("http://acme.com/~clauret"));
    assertTrue(x.match("http://acme.com/%45"));
    assertFalse(x.match("http://acme.com/toast/"));
  }

  /**
   * Test the <code>match</code> method 
   */
  public void testMatchDouble() {
    URIPattern y = new URIPattern("http://acme.com/{X}/{Y}/home");
    assertTrue(y.match("http://acme.com/user/clauret/home"));
    assertTrue(y.match("http://acme.com/dir-x/_/home"));
    assertFalse(y.match("http://acme.com/toast//home"));
  }

  /**
   * Test the <code>match</code> method 
   */
  public void testMatchTyped() {
    URIPattern x = new URIPattern("http://acme.com/{t:X}");
    assertTrue(x.match("http://acme.com/toast"));
    assertTrue(x.match("http://acme.com/~clauret"));
    assertTrue(x.match("http://acme.com/%45"));
    assertFalse(x.match("http://acme.com/toast/"));
  }

  /**
   * Test the <code>match</code> method 
   */
  public void testMatch_URIInsert() {
    URIPattern x = new URIPattern("http://acme.com/{+X}");
    assertTrue(x.match("http://acme.com/this/is/a/path"));
    assertTrue(x.match("http://acme.com/email@acme.com"));
  }

  /**
   * Test the <code>match</code> method 
   */
  public void testMatch_Wildcard() {
    URIPattern x = new URIPattern("http://acme.com/*");
    assertTrue(x.match("http://acme.com/this/is/a/path"));
    assertTrue(x.match("http://acme.com/email@acme.com"));
    assertTrue(x.match("http://acme.com/dir/subdir/doc.html"));
  }

  /**
   * Test the <code>match</code> method 
   */
  public void testMatch_PathParameter() {
    URIPattern y = new URIPattern("http://acme.com/filter{;x,y,z}/list");
    assertTrue(y.match("http://acme.com/filter;x=1;y=2;z=5/list"));
    assertTrue(y.match("http://acme.com/filter;y=1;z=2;x=5/list"));
    assertTrue(y.match("http://acme.com/filter;y=1;z=2/list"));
    assertTrue(y.match("http://acme.com/filter;y=1;z=2/list"));
  }

}
