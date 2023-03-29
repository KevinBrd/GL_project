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
    * Prend deux objets Color et renvoie un booléen indiquant si les couleurs sont suffisamment proches pour 
    être considérées comme correspondantes.
    * @param color0 Première couleur
    * @param color1 Seconde couleur
    * @return Boolean, true si les couleurs sont considérées comme correspondantes.
    */
    @Override
    public boolean matchColors(Color color0, Color color1) {
        double colorDistSq = ColorUtils.calculateColorDistSq(color0, color1);
        return colorDistSq < minColorDistSq;
    }

    /**
    * Crée une image vide en fonction des dimensions de deux images données en entrée.
    * @param image0 Première image
    * @param image1 Seconde image
    * @return Objet WritableImage
    */
    @Override
    public WritableImage createEmptyMatchImage(Image image0,
                                               Image image1) {
        return new WritableImage((int) image0.getWidth(), (int) image1.getHeight());
    }

    /**
    * Prend deux couleurs en entrée et renvoie une nouvelle couleur
    qui est un mélange de gris et de blanc en fonction de la différence entre les deux couleurs.
    * @param color0 Première couleur
    * @param color1 Seconde couleur
    * @return Objet Couleur
    */
    @Override
    public Color createMatchColor(Color color0, Color color1) {
        double gray = color0.grayscale().getRed();
        double opacity = color0.getOpacity();
        return Color.gray(blendToWhite(gray, colorBlendFactor), opacity);
    }

    /**
    * Prend un niveau de gris et un facteur de mélange en entrée et renvoie
    une valeur pondérée de gris qui est mélangée avec du blanc.
    * @param gray Niveau de gris
    * @param factor Facteur de mélange
    * @return double, valeur pondérée de gris qui est mélangée avec du blanc
    */
    private double blendToWhite(double gray, double factor) {
        return ((1.0 - factor) * gray) + factor;
    }

}
