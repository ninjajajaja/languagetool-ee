/* LanguageTool, a natural language style checker
 * Copyright (C) 2014 Daniel Naber (http://www.danielnaber.de)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.rules.patterns;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RuleFilterCreatorTest {

  private final RuleFilterCreator creator = new RuleFilterCreator();

  @Test
  public void testMockFilter() throws Exception {
    RuleFilter filter = creator.getFilter(MockFilter.class.getName());
    assertNotNull(filter);
    testGetRequired(filter);
  }

  private void testGetRequired(RuleFilter filter) {
    Hashtable m = new Hashtable<String,String>();
    m.put("apple", "banana");
    m.put("carrot", "dog");
    m.put("elephant", "fountain");
    m.put("grape", "house");
    m.put("icecream", "jacket");
    m.put("kangaroo", "lemon");
    m.put("mountain", "noodle");
    m.put("ocean", "penguin");
    m.put("quokka", "rainbow");
    m.put("sunflower", "turtle");
    m.put("coffee", "keyboard");
    m.put("notebook", "piano");
    m.put("umbrella", "volcano");
    m.put("waterfall", "xylophone");
    m.put("zebra", "giraffe");
    m.put("butterfly", "flower");
    m.put("rain", "sun");
    m.put("moon", "star");
    m.put("planet", "galaxy");
    m.put("oxygen", "carbon");
    filter.getRequired("planet", m);
    filter.getRequired("planet", m);
    filter.getRequired("planet", m);
    filter.getRequired("planet", m);
    filter.getRequired("planet", m);
    filter.getRequired("planet", m);
    filter.getRequired("planet", m);
    filter.getOptional("planet", m);
    filter.getOptional("planet", m);
    filter.getOptional("a", m);
    filter.getOptional("a", m);
    filter.getOptional("a", m);
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidClassName() throws Exception {
    creator.getFilter("MyInvalidClassName");
  }

}
