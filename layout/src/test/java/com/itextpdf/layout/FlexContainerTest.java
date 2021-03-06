/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
    Authors: iText Software.

    This program is offered under a commercial and under the AGPL license.
    For commercial licensing, contact us at https://itextpdf.com/sales.  For AGPL licensing, see below.

    AGPL licensing:
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.itextpdf.layout;

import com.itextpdf.io.LogMessageConstant;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.FlexContainer;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.Background;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.renderer.FlexContainerRenderer;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.LogMessage;
import com.itextpdf.test.annotations.LogMessages;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class FlexContainerTest extends ExtendedITextTest {
    public static float EPS = 0.001f;

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/layout/FlexContainerTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/layout/FlexContainerTest/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void defaultFlexContainerTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "defaultFlexContainerTest.pdf";
        String cmpFileName = sourceFolder + "cmp_defaultFlexContainerTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.MARGIN_TOP, UnitValue.createPointValue(50));
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.PADDING_LEFT, UnitValue.createPointValue(40));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        flexContainer.add(createNewDiv()).add(createNewDiv()).add(innerDiv).add(createNewDiv()).add(createNewDiv());

        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerFixedHeightWidthTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerFixedHeightWidthTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerFixedHeightWidthTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.MARGIN_TOP, UnitValue.createPointValue(50));
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.PADDING_LEFT, UnitValue.createPointValue(40));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setProperty(Property.WIDTH, UnitValue.createPointValue(500));
        flexContainer.setProperty(Property.HEIGHT, UnitValue.createPointValue(500));

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        flexContainer.add(createNewDiv()).add(createNewDiv()).add(innerDiv).add(createNewDiv()).add(createNewDiv());

        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerDifferentChildrenTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerDifferentChildrenTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerDifferentChildrenTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setProperty(Property.WIDTH, UnitValue.createPointValue(500));

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));

        Table table = new Table(UnitValue.createPercentArray(new float[] {10, 10, 10}));
        for (int i = 0; i < 3; i++) {
            table.addCell("Hello");
        }

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");
        romanList.setProperty(Property.BACKGROUND, new Background(ColorConstants.MAGENTA));

        flexContainer.add(table).add(new Paragraph("Test")).add(innerDiv).add(romanList).add(new Image(ImageDataFactory.create(sourceFolder + "img.jpg")));
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerHeightClippedTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerHeightClippedTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerHeightClippedTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setProperty(Property.WIDTH, UnitValue.createPointValue(500));
        flexContainer.setHeight(250);

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));

        Table table = new Table(UnitValue.createPercentArray(new float[] {10, 10, 10}));
        for (int i = 0; i < 3; i++) {
            table.addCell("Hello");
        }

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");
        romanList.setProperty(Property.BACKGROUND, new Background(ColorConstants.MAGENTA));

        flexContainer.add(table).add(new Paragraph("Test")).add(innerDiv).add(romanList).add(new Image(ImageDataFactory.create(sourceFolder + "img.jpg")));
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    @LogMessages(messages = @LogMessage(messageTemplate = LogMessageConstant.ELEMENT_DOES_NOT_FIT_AREA))
    // TODO DEVSIX-5042 HEIGHT property is ignored when FORCED_PLACEMENT is true
    public void flexContainerDifferentChildrenDontFitHorizontallyTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerDifferentChildrenDontFitHorizontallyTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerDifferentChildrenDontFitHorizontallyTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setProperty(Property.WIDTH, UnitValue.createPointValue(300));

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));

        Table table = new Table(UnitValue.createPercentArray(new float[] {10, 10, 10}));
        for (int i = 0; i < 3; i++) {
            table.addCell("Hello");
        }

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");

        flexContainer.add(table).add(new Paragraph("Test")).add(innerDiv).add(romanList).add(new Image(ImageDataFactory.create(sourceFolder + "img.jpg")));
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    // TODO DEVSIX-5042 HEIGHT property is ignored when FORCED_PLACEMENT is true
    public void flexContainerDifferentChildrenDontFitHorizontallyForcedPlacementTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerDifferentChildrenDontFitHorizontallyForcedPlacementTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerDifferentChildrenDontFitHorizontallyForcedPlacementTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setProperty(Property.FORCED_PLACEMENT, true);

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));

        Table table = new Table(UnitValue.createPercentArray(new float[] {25, 25, 25, 25}));
        for (int i = 0; i < 4; i++) {
            table.addCell("Hello");
        }

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("MuchMoreText").add("MuchMoreText").add("MuchMoreText");

        flexContainer.add(table).add(new Paragraph("MuchMoreText")).add(innerDiv).add(romanList).add(new Image(ImageDataFactory.create(sourceFolder + "img.jpg")));
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerDifferentChildrenDontFitVerticallyTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerDifferentChildrenDontFitVerticallyTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerDifferentChildrenDontFitVerticallyTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setProperty(Property.WIDTH, UnitValue.createPointValue(600));
        flexContainer.setHeight(400);

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));

        Table table = new Table(UnitValue.createPercentArray(new float[] {10, 10, 10}));
        for (int i = 0; i < 3; i++) {
            table.addCell("Hello");
        }

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");

        flexContainer.add(table).add(new Paragraph("Test")).add(innerDiv).add(romanList).add(new Image(ImageDataFactory.create(sourceFolder + "img.jpg")));
        Div prevDiv = new Div();
        prevDiv.setHeight(480);
        prevDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.RED));
        document.add(prevDiv);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerDifferentChildrenFitContainerDoesNotFitVerticallyTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerDifferentChildrenFitContainerDoesNotFitVerticallyTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerDifferentChildrenFitContainerDoesNotFitVerticallyTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setHeight(600);

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));

        Table table = new Table(UnitValue.createPercentArray(new float[] {10, 10, 10}));
        for (int i = 0; i < 3; i++) {
            table.addCell("Hello");
        }

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");

        flexContainer.add(table).add(new Paragraph("Test")).add(innerDiv).add(romanList).add(new Image(ImageDataFactory.create(sourceFolder + "img.jpg")));
        Div prevDiv = new Div();
        prevDiv.setHeight(400);
        prevDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.RED));
        document.add(prevDiv);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerDifferentChildrenWithGrowTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerDifferentChildrenWithGrowTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerDifferentChildrenWithGrowTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setWidth(500);

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        innerDiv.setProperty(Property.FLEX_GROW, 1f);

        Table table = new Table(UnitValue.createPercentArray(new float[] {50, 50}));
        for (int i = 0; i < 2; i++) {
            table.addCell("Hello");
        }
        table.setProperty(Property.FLEX_GROW, 1f);

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");
        romanList.setProperty(Property.FLEX_GROW, 1f);
        romanList.setProperty(Property.BACKGROUND, new Background(ColorConstants.MAGENTA));

        Image img = new Image(ImageDataFactory.create(sourceFolder + "img.jpg"));
        img.setProperty(Property.FLEX_GROW, 1f);

        flexContainer.add(table).add(innerDiv).add(romanList).add(img);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerDifferentChildrenWithFlexBasisTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerDifferentChildrenWithFlexBasisTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerDifferentChildrenWithFlexBasisTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setWidth(500);

        Table table = new Table(UnitValue.createPercentArray(new float[] {50, 50}));
        for (int i = 0; i < 2; i++) {
            table.addCell("Hello");
        }
        table.setProperty(Property.FLEX_BASIS, UnitValue.createPointValue(150));

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");
        romanList.setProperty(Property.BACKGROUND, new Background(ColorConstants.MAGENTA));
        romanList.setProperty(Property.FLEX_BASIS, UnitValue.createPointValue(100));

        Image img = new Image(ImageDataFactory.create(sourceFolder + "img.jpg"));
        img.setProperty(Property.FLEX_BASIS, UnitValue.createPointValue(150));

        flexContainer.add(table).add(romanList).add(img);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerDifferentChildrenWithFlexShrinkTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerDifferentChildrenWithFlexShrinkTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerDifferentChildrenWithFlexShrinkTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setWidth(500);

        Table table = new Table(UnitValue.createPercentArray(new float[] {50, 50}));
        for (int i = 0; i < 2; i++) {
            table.addCell("Hello");
        }
        table.setProperty(Property.FLEX_BASIS, UnitValue.createPointValue(200));
        table.setProperty(Property.FLEX_SHRINK, 0f);

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");
        romanList.setProperty(Property.BACKGROUND, new Background(ColorConstants.MAGENTA));
        romanList.setProperty(Property.FLEX_BASIS, UnitValue.createPointValue(200));
        romanList.setProperty(Property.FLEX_SHRINK, 0f);

        Div div = new Div().add(new Paragraph("Test"));
        div.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        div.setProperty(Property.FLEX_BASIS, UnitValue.createPointValue(200));

        flexContainer.add(table).add(romanList).add(div);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerInsideFlexContainerTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerInsideFlexContainerTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerInsideFlexContainerTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));

        Div innerFlex = new FlexContainer();
        innerFlex.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.2f);
        }
        innerFlex.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        innerFlex.setProperty(Property.FLEX_GROW, 0.7f);

        flexContainer.add(innerFlex).add(createNewDiv());
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerInsideFlexContainerWithHugeBordersTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerInsideFlexContainerWithHugeBordersTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerInsideFlexContainerWithHugeBordersTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(ColorConstants.BLUE,20));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));

        Div innerFlex = new FlexContainer();
        innerFlex.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 1f);
            children.setProperty(Property.BORDER, new SolidBorder(ColorConstants.YELLOW,10));
        }
        innerFlex.setProperty(Property.BACKGROUND, new Background(ColorConstants.RED));
        innerFlex.setProperty(Property.FLEX_GROW, 1f);
        innerFlex.setProperty(Property.BORDER, new SolidBorder(ColorConstants.RED,15));

        flexContainer.add(innerFlex).add(createNewDiv().setBorder(new SolidBorder(ColorConstants.GREEN,10)));
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void multipleFlexContainersInsideFlexContainerTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "multipleFlexContainersInsideFlexContainerTest.pdf";
        String cmpFileName = sourceFolder + "cmp_multipleFlexContainersInsideFlexContainerTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));

        Div innerFlex1 = new FlexContainer();
        innerFlex1.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex1.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.2f);
        }
        innerFlex1.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        innerFlex1.setProperty(Property.FLEX_GROW, 1f);

        Div innerFlex2 = new FlexContainer();
        innerFlex2.add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex2.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.3f);
        }
        innerFlex2.setProperty(Property.BACKGROUND, new Background(ColorConstants.RED));
        innerFlex2.setProperty(Property.FLEX_GROW, 2f);

        flexContainer.add(innerFlex1).add(innerFlex2);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void multipleFlexContainersWithPredefinedPointWidthsInsideFlexContainerTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "multipleFlexContainersWithPredefinedPointWidthsInsideFlexContainerTest.pdf";
        String cmpFileName = sourceFolder + "cmp_multipleFlexContainersWithPredefinedPointWidthsInsideFlexContainerTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));

        Div innerFlex1 = new FlexContainer();
        innerFlex1.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex1.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.2f);
        }
        innerFlex1.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        innerFlex1.setProperty(Property.FLEX_GROW, 1f);
        innerFlex1.setWidth(380);

        Div innerFlex2 = new FlexContainer();
        innerFlex2.add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex2.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.3f);
        }
        innerFlex2.setProperty(Property.BACKGROUND, new Background(ColorConstants.RED));
        innerFlex2.setProperty(Property.FLEX_GROW, 2f);
        innerFlex2.setWidth(200);

        flexContainer.add(innerFlex1).add(innerFlex2);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void multipleFlexContainersWithPredefinedPercentWidthsInsideFlexContainerTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "multipleFlexContainersWithPredefinedPercentWidthsInsideFlexContainerTest.pdf";
        String cmpFileName = sourceFolder + "cmp_multipleFlexContainersWithPredefinedPercentWidthsInsideFlexContainerTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));

        Div innerFlex1 = new FlexContainer();
        innerFlex1.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex1.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.2f);
        }
        innerFlex1.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        innerFlex1.setProperty(Property.FLEX_GROW, 1f);
        innerFlex1.setWidth(UnitValue.createPercentValue(40));

        Div innerFlex2 = new FlexContainer();
        innerFlex2.add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex2.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.3f);
        }
        innerFlex2.setProperty(Property.BACKGROUND, new Background(ColorConstants.RED));
        innerFlex2.setProperty(Property.FLEX_GROW, 2f);
        innerFlex2.setWidth(UnitValue.createPercentValue(40));

        flexContainer.add(innerFlex1).add(innerFlex2);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    // TODO DEVSIX-5087 Content should not overflow container by default
    public void multipleFlexContainersWithPredefinedMinWidthsInsideFlexContainerTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "multipleFlexContainersWithPredefinedMinWidthsInsideFlexContainerTest.pdf";
        String cmpFileName = sourceFolder + "cmp_multipleFlexContainersWithPredefinedMinWidthsInsideFlexContainerTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));

        Div innerFlex1 = new FlexContainer();
        innerFlex1.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex1.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.2f);
        }
        innerFlex1.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        innerFlex1.setProperty(Property.FLEX_GROW, 1f);
        innerFlex1.setProperty(Property.MIN_WIDTH, UnitValue.createPointValue(380));

        Div innerFlex2 = new FlexContainer();
        innerFlex2.add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex2.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.3f);
        }
        innerFlex2.setProperty(Property.BACKGROUND, new Background(ColorConstants.RED));
        innerFlex2.setProperty(Property.FLEX_GROW, 2f);
        innerFlex2.setProperty(Property.MIN_WIDTH, UnitValue.createPointValue(200));

        flexContainer.add(innerFlex1).add(innerFlex2);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void multipleFlexContainersWithPredefinedMaxWidthsInsideFlexContainerTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "multipleFlexContainersWithPredefinedMaxWidthsInsideFlexContainerTest.pdf";
        String cmpFileName = sourceFolder + "cmp_multipleFlexContainersWithPredefinedMaxWidthsInsideFlexContainerTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));

        Div innerFlex1 = new FlexContainer();
        innerFlex1.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex1.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.2f);
        }
        innerFlex1.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));
        innerFlex1.setProperty(Property.FLEX_GROW, 1f);
        innerFlex1.setProperty(Property.MAX_WIDTH, UnitValue.createPointValue(200));

        Div innerFlex2 = new FlexContainer();
        innerFlex2.add(createNewDiv()).add(createNewDiv());
        for (IElement children : innerFlex2.getChildren()) {
            children.setProperty(Property.FLEX_GROW, 0.3f);
        }
        innerFlex2.setProperty(Property.BACKGROUND, new Background(ColorConstants.RED));
        innerFlex2.setProperty(Property.FLEX_GROW, 2f);
        innerFlex2.setProperty(Property.MAX_WIDTH, UnitValue.createPointValue(200));

        flexContainer.add(innerFlex1).add(innerFlex2);
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerFillAvailableAreaTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerFillAvailableAreaTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerFillAvailableAreaTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setProperty(Property.FILL_AVAILABLE_AREA, true);

        Div innerDiv = new Div();
        innerDiv.add(createNewDiv()).add(createNewDiv()).add(createNewDiv());
        innerDiv.setProperty(Property.BACKGROUND, new Background(ColorConstants.GREEN));

        Table table = new Table(UnitValue.createPercentArray(new float[] {10, 10, 10}));
        for (int i = 0; i < 3; i++) {
            table.addCell("Hello");
        }

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");
        romanList.setProperty(Property.BACKGROUND, new Background(ColorConstants.MAGENTA));

        flexContainer.add(table).add(new Paragraph("Test")).add(innerDiv).add(romanList).add(new Image(ImageDataFactory.create(sourceFolder + "img.jpg")));
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void flexContainerRotationAngleTest() throws IOException, InterruptedException {
        String outFileName = destinationFolder + "flexContainerRotationAngleTest.pdf";
        String cmpFileName = sourceFolder + "cmp_flexContainerRotationAngleTest.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outFileName));

        Document document = new Document(pdfDocument);

        Div flexContainer = new FlexContainer();
        flexContainer.setProperty(Property.BORDER, new SolidBorder(2));
        flexContainer.setProperty(Property.BACKGROUND, new Background(ColorConstants.LIGHT_GRAY));
        flexContainer.setProperty(Property.WIDTH, UnitValue.createPointValue(400));
        flexContainer.setProperty(Property.ROTATION_ANGLE, 20f);

        Table table = new Table(UnitValue.createPercentArray(new float[] {10, 10, 10}));
        for (int i = 0; i < 3; i++) {
            table.addCell("Hello");
        }

        List romanList = new List(ListNumberingType.ROMAN_LOWER).setSymbolIndent(20).
                setMarginLeft(25).
                add("One").add("Two").add("Three");
        romanList.setProperty(Property.BACKGROUND, new Background(ColorConstants.MAGENTA));

        flexContainer.add(table).add(new Paragraph("Test")).add(romanList).add(new Image(ImageDataFactory.create(sourceFolder + "img.jpg")));
        document.add(flexContainer);

        document.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    private static Div createNewDiv() {
        Div newDiv = new Div();
        newDiv.setProperty(Property.BORDER, new SolidBorder(1));
        newDiv.setProperty(Property.WIDTH, UnitValue.createPointValue(50));
        newDiv.setProperty(Property.HEIGHT, UnitValue.createPointValue(100));
        return newDiv;
    }
}
