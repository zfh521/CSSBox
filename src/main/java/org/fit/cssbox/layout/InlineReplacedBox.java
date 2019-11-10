/**
 * InlineReplacedBox.java
 * Copyright (c) 2005-2007 Radek Burget
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
 * Created on 27.9.2006, 21:08:14 by radek
 */
package org.fit.cssbox.layout;

import org.w3c.dom.Element;

/**
 * Inline replaced box.
 * @author radek
 */
public class InlineReplacedBox extends InlineBox implements ReplacedBox
{
    protected float boxw; //image width attribute
    protected float boxh; //image height attribute
    protected ReplacedContent obj; //the contained object
    
    /** 
     * Creates a new instance of ImgBox 
     */
    public InlineReplacedBox(Element el, VisualContext ctx)
    {
        super(el, ctx);
        lineHeight = boxh;
    }
    
    /**
	 * @return the content object
	 */
	public ReplacedContent getContentObj()
	{
		return obj;
	}

	/**
	 * @param obj the obj to set
	 */
	public void setContentObj(ReplacedContent obj)
	{
		this.obj = obj;
		isempty = (obj == null);
		if (!isempty)
		    obj.setOwner(this);
	}

    @Override
    public float getContentObjWidth()
    {
        return boxw;
    }

    @Override
    public float getContentObjHeight()
    {
        return boxh;
    }

    @Override
    public float getMaximalWidth()
    {
        return boxw + margin.left + padding.left + border.left + 
                margin.right + padding.right + border.right;
    }

    @Override
    public float getMinimalWidth()
    {
        return boxw + margin.left + padding.left + border.left + 
                margin.right + padding.right + border.right;
    }
    
    @Override
    public Rectangle getMinimalAbsoluteBounds()
    {
        return new Rectangle(getAbsoluteContentX(), getAbsoluteContentY(), boxw, boxh);
    }

    @Override
    public boolean isWhitespace()
    {
        return false;
    }

    @Override
    public boolean isReplaced()
    {
        return true;
    }

    @Override
    public boolean canSplitAfter()
    {
        return true;
    }

    @Override
    public boolean canSplitBefore()
    {
        return true;
    }

    @Override
    public boolean canSplitInside()
    {
        return false;
    }

    @Override
	public float getBaselineOffset()
	{
    	return boxh;
	}

	@Override
	public float getBelowBaseline()
	{
		return 0;
	}

	@Override
	public float getTotalLineHeight()
	{
		return boxh;
	}
	
	/*@Override
	public float getLineboxOffset()
	{
	    return boxh - ctx.getBaselineOffset();
	}*/
	
	@Override
	public float getMaxLineHeight()
	{
	    return boxh;
	}
	
	@Override
    public boolean marginsAdjoin()
    {
	    if (getContentObjHeight() > 0)
	        return false; //margins separated by the content
	    else
	        return super.marginsAdjoin();
    }

    @Override
    public boolean doLayout(float availw, boolean force, boolean linestart) 
    {
        //Skip if not displayed
        if (!displayed)
        {
            content.setSize(0, 0);
            bounds.setSize(0, 0);
            return true;
        }

        setAvailableWidth(availw);
        float wlimit = getAvailableContentWidth();
        if (getWidth() <= wlimit)
            return true;
        else
            return force;
    }

	@Override
    protected void loadSizes()
    {
        super.loadSizes();
        loadSizeInfo();
    }
    
    @Override
    public void updateSizes()
    {
        loadSizeInfo();
    }
    
    private void loadSizeInfo()
    {
        Rectangle objsize = CSSDecoder.computeReplacedObjectSize(obj, this);
        content.width = boxw = objsize.width;
        content.height = boxh = objsize.height;
        bounds.setSize(totalWidth(), totalHeight());
    }

    @Override
	public void draw(DrawStage turn)
    {
        if (displayed && isVisible())
        {
            if (!this.formsStackingContext())
            {
                switch (turn)
                {
                    case DRAW_NONINLINE:
                    case DRAW_FLOAT:
                        //there should be no block-level or floating children here -- we do nothing
                        break;
                    case DRAW_INLINE:
                        getViewport().getRenderer().renderElementBackground(this);
                        getViewport().getRenderer().startElementContents(this);
                        getViewport().getRenderer().renderReplacedContent(this);
                        getViewport().getRenderer().finishElementContents(this);
                        break;
                }
            }
        }
    }

}
