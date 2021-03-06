/*
 * Copyright 2014, Stratio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.cassandra.index.query.builder;

import com.stratio.cassandra.index.query.RangeCondition;

/**
 * {@link ConditionBuilder} for building a new {@link com.stratio.cassandra.index.query.RangeCondition}.
 *
 * @author Andres de la Pena <adelapena@stratio.com>
 */
public class RangeConditionBuilder extends ConditionBuilder<RangeCondition, RangeConditionBuilder>
{

    private final String field;
    private Object lower;
    private Object upper;
    private Boolean includeLower;
    private Boolean includeUpper;

    /**
     * Creates a new {@link RangeConditionBuilder} for the specified field.
     *
     * @param field the name of the field to be matched.
     */
    public RangeConditionBuilder(String field)
    {
        this.field = field;
    }

    /**
     * Sets the lower value to be matched.
     *
     * @param lower The lower value to be matched.
     * @return This builder with the specified lower value to be matched.
     */
    public RangeConditionBuilder lower(Object lower)
    {
        this.lower = lower;
        return this;
    }

    /**
     * Sets the upper value to be matched.
     *
     * @param upper The lower value to be matched.
     * @return This builder with the specified upper value to be matched.
     */
    public RangeConditionBuilder upper(Object upper)
    {
        this.upper = upper;
        return this;
    }

    /**
     * Sets if the lower value must be included.
     *
     * @param includeLower If the lower value must be included.
     * @return This builder with the specified lower interval option.
     */
    public RangeConditionBuilder includeLower(Boolean includeLower)
    {
        this.includeLower = includeLower;
        return this;
    }

    /**
     * Sets if the upper value must be included.
     *
     * @param includeUpper If the upper value must be included.
     * @return This builder with the specified upper interval option.
     */
    public RangeConditionBuilder includeUpper(Boolean includeUpper)
    {
        this.includeUpper = includeUpper;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeCondition build()
    {
        return new RangeCondition(boost, field, lower, upper, includeLower, includeUpper);
    }
}
