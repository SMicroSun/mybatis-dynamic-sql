/**
 *    Copyright 2016-2017 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.dynamic.sql.util;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class FragmentCollectorTest {

    @Test
    public void testWhereFragmentCollectorMerge() {
        SoftAssertions.assertSoftly(softly -> {
            FragmentCollector fc1 = new FragmentCollector();
            FragmentAndParameters fp1 = new FragmentAndParameters.Builder()
                    .withFragment(":p1")
                    .withParameter("p1", 1)
                    .build();
            fc1.add(fp1);

            FragmentCollector fc2 = new FragmentCollector();
            FragmentAndParameters fp2 = new FragmentAndParameters.Builder()
                    .withFragment(":p2")
                    .withParameter("p2", 2)
                    .build();
            fc2.add(fp2);

            fc1 = fc1.merge(fc2);

            softly.assertThat(fc1.fragments.size()).isEqualTo(2);
            softly.assertThat(fc1.fragments.get(0)).isEqualTo(":p1");
            softly.assertThat(fc1.fragments.get(1)).isEqualTo(":p2");

            softly.assertThat(fc1.parameters.size()).isEqualTo(2);
            softly.assertThat(fc1.parameters.get("p1")).isEqualTo(1);
            softly.assertThat(fc1.parameters.get("p2")).isEqualTo(2);
        });
    }
}
