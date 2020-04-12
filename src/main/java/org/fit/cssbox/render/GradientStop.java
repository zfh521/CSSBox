/*
 * GradientStop.java
 * Copyright (c) 2005-2020 Radek Burget
 *
 * CSSBox is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * CSSBox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *  
 * You should have received a copy of the GNU Lesser General Public License
 * along with CSSBox. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fit.cssbox.render;

import cz.vutbr.web.csskit.Color;

/**
 * A single color stop representation.
 * 
 * @author Martin Safar
 * @author burgetr
 */
public class GradientStop
{
    private Color color;
    private Float percentage;

    public GradientStop(Color color, Float percentage)
    {
        this.color = color;
        this.percentage = percentage;
    }

    public Color getColor()
    {
        return color;
    }

    public Float getPercentage()
    {
        return percentage;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void setPercentage(Float percentage)
    {
        this.percentage = percentage;
    }

}
