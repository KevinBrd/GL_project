/*
 * Copyright 2013-2014 SmartBear Software
 * Copyright 2014-2021 The TestFX Contributors
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by the
 * European Commission - subsequent versions of the EUPL (the "Licence"); You may
 * not use this work except in compliance with the Licence.
 *
 * You may obtain a copy of the Licence at:
 * http://ec.europa.eu/idabc/eupl.html
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the Licence for the
 * specific language governing permissions and limitations under the Licence.
 */
package org.testfx.service.support.impl;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import org.testfx.util.ColorUtils;

public class PixelMatcherRgb extends PixelMatcherBase{

    private final double colorBlendFactor;
    private final double minColorDistSq;

    public PixelMatcherRgb() {
        this(0.20, 0.75);
    }

    public PixelMatcherRgb(double minColorDistFactor, double colorBlendFactor) {
        this.colorBlendFactor = colorBlendFactor;
        double maxColorDistSq = ColorUtils.calculateColorDistSq(Color.BLACK, Color.WHITE);
        minColorDistSq = maxColorDistSq * (minColorDistFactor * minColorDistFactor);
    }

    /**
    Takes two Color objects and returns a boolean indicating whether the colors are close enough to be considered matching.
    @param color0 First color
    @param color1 Second color
    @return Boolean, true if the colors are considered matching.
    */
    @Override
    public boolean matchColors(Color color0, Color color1) {
        double colorDistSq = ColorUtils.calculateColorDistSq(color0, color1);
        return colorDistSq < minColorDistSq;
    }

    /**
    Creates an empty image based on the dimensions of two input images.
    @param image0 First image
    @param image1 Second image
    @return WritableImage object
    */
    @Override
    public WritableImage createEmptyMatchImage(Image image0,
                                               Image image1) {
        return new WritableImage((int) image0.getWidth(), (int) image1.getHeight());
    }

    /**
    Takes two colors as input and returns a new color that is a blend of gray and white based on the difference between the two colors.
    @param color0 First color
    @param color1 Second color
    @return Color object
    */
    @Override
    public Color createMatchColor(Color color0, Color color1) {
        double gray = color0.grayscale().getRed();
        double opacity = color0.getOpacity();
        return Color.gray(blendToWhite(gray, colorBlendFactor), opacity);
    }

    /**
    Takes a gray level and a blend factor as input and returns a weighted value of gray that is blended with white.
    @param gray Gray level
    @param factor Blend factor
    @return double, weighted value of gray that is blended with white
    */
    private double blendToWhite(double gray, double factor) {
        return ((1.0 - factor) * gray) + factor;
    }

}
