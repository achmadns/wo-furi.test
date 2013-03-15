/*
 * This file is part of the URI Template library.
 *
 * For licensing information please see the file license.txt included in the release.
 * A copy of this licence can also be found at 
 *   http://www.opensource.org/licenses/artistic-license-2.0.php
 */
package org.weborganic.furi;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Invokes all the test for this package in the correct order.
 * 
 * @author Christophe Lauret
 * @version 2 January 2009
 */
public class URITemplateTestSuite {

  /**
   * {@inheritDoc}
   */
  public static Test suite() {
    TestSuite suite = new TestSuite("URI Templates Test Suite");
    // core utility classes
    suite.addTestSuite(VariableTest.class);
    suite.addTestSuite(URICoderTest.class);
    // tokens
    suite.addTestSuite(TokenBaseTest.class);
    suite.addTestSuite(TokenLiteralTest.class);
    suite.addTestSuite(TokenVariableTest.class);
    suite.addTestSuite(SyntaxDraft3Test.class);
    suite.addTestSuite(SyntaxDraftXTest.class);
    suite.addTestSuite(SyntaxPageSeederTest.class);
    // token factory
    suite.addTestSuite(TokenFactoryTest.class);
    // URI objects
    suite.addTestSuite(URITemplateTest.class);
    suite.addTestSuite(URIPatternTest.class);
    // resolver
    suite.addTestSuite(URIResolverTest.class);
    return suite;
  }

}
