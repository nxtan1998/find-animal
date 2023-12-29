package ze.spineactor.esotericsoftware.spine.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;

public interface PolygonBatch extends Batch {
    /** Draws a polygon region with the bottom left corner at x,y having the width and height of the region. */
    void draw(PolygonRegion region, float x, float y);

    /** Draws a polygon region with the bottom left corner at x,y and stretching the region to cover the given width and height. */
    void draw(PolygonRegion region, float x, float y, float width, float height);

    /** Draws the polygon region with the bottom left corner at x,y and stretching the region to cover the given width and height.
     * The polygon region is offset by originX, originY relative to the origin. Scale specifies the scaling factor by which the
     * polygon region should be scaled around originX, originY. Rotation specifies the angle of counter clockwise rotation of the
     * rectangle around originX, originY. */
    void draw(PolygonRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX,
              float scaleY, float rotation);

    /** Draws the polygon using the given vertices and triangles. Each vertices must be made up of 5 elements in this order: x, y,
     * color, u, v. */
    void draw(Texture texture, float[] polygonVertices, int verticesOffset, int verticesCount, short[] polygonTriangles,
              int trianglesOffset, int trianglesCount);
    public void setPackedColor(float packedColor);
}
