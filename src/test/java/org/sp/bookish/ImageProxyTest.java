package org.sp.bookish;

import org.junit.jupiter.api.Test;
import org.sp.bookish.entity.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ImageProxyTest {

    @Test
    void testLazyLoading() {
        Dimension dim = new Dimension(800, 600);
        ImageProxy proxy = new ImageProxy("heavy_diagram.png", dim);

        // Before accessing content or printing, real image should be null
        assertNull(proxy.getRealImg());
        assertEquals("heavy_diagram.png", proxy.url());
        assertEquals(dim, proxy.dim());

        // Call loadImage explicitly
        Image realImg = proxy.loadImage();
        assertNotNull(realImg);
        assertSame(realImg, proxy.getRealImg());
        assertEquals("heavy_diagram.png", realImg.url());
        assertEquals(dim, realImg.dim());
        assertNotNull(realImg.content());

        // Subsequent loadImage calls should return the same cached instance
        assertSame(realImg, proxy.loadImage());
    }

    @Test
    void testPrintDelegation() {
        ImageProxy proxy = new ImageProxy("sample.png");
        assertNull(proxy.getRealImg());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            proxy.print();
            assertEquals("Image with name: sample.png" + System.lineSeparator(), outContent.toString());
        } finally {
            System.setOut(System.out);
        }

        // realImg should have been instantiated
        assertNotNull(proxy.getRealImg());
    }

    @Test
    void testContentDelegation() {
        ImageProxy proxy = new ImageProxy("photo.jpg");
        assertNull(proxy.getRealImg());

        PictureContent content = proxy.content();
        assertNotNull(content);
        assertNotNull(proxy.getRealImg());
        assertSame(proxy.getRealImg().content(), content);
    }

    @Test
    void testCompositeIntegration() {
        Section section = new Section("Gallery");
        ImageProxy proxy = new ImageProxy("art.png");

        section.add(proxy);
        assertEquals(1, section.getChildren().size());
        assertSame(proxy, section.get(0));

        // Unsupported operations on leaf proxy
        assertThrows(UnsupportedOperationException.class, () -> proxy.add(new Image("child.png")));
        assertThrows(UnsupportedOperationException.class, () -> proxy.remove(new Image("child.png")));
        assertThrows(UnsupportedOperationException.class, () -> proxy.get(0));

        assertDoesNotThrow(section::print);
    }
}
