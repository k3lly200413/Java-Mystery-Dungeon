package it.unibo.progetto_oop.combat.mvc_pattern;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.Serial;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

// Source: https://gist.github.com/jirkapenzes/4560255
/**
 * A FlowLayout subclass that fully supports wrapping of components.
 */
public class WrapLayout extends FlowLayout {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new WrapLayout with a left alignment and a
     * default 5-unit horizontal and vertical gap.
     */
    public WrapLayout() {
        super();
    }

    /**
     * Constructs a new WrapLayout with the specified alignment.
     *
     * @param align the alignment value
     */
    public WrapLayout(final int align) {
        super(align);
    }

    /**
     * Constructs a new WrapLayout with the specified alignment
     * and the specified horizontal and vertical gaps.
     *
     * @param align the alignment value
     * @param hgap the horizontal gap
     * @param vgap the vertical gap
     */
    public WrapLayout(final int align, final int hgap, final int vgap) {
        super(align, hgap, vgap);
    }

    /**
     * Returns the preferred dimensions for this layout given the
     * visible components in the specified target container.
     *
     * @param target the component which needs to be laid out
     * @return the preferred dimensions to lay out the subcomponents of the
     *         specified container
     */
    @Override
    public final Dimension preferredLayoutSize(final Container target) {
        return layoutSize(target, true);
    }

    /**
     * Returns the minimum dimensions needed to layout the visible
     * components contained in the specified target container.
     *
     * @param target the component which needs to be laid out
     * @return the minimum dimensions to lay out the subcomponents of the
     *         specified container
     */
    @Override
    public final Dimension minimumLayoutSize(final Container target) {
        final Dimension minimum = layoutSize(target, false);
        minimum.width -= getHgap() + 1;
        return minimum;
    }

    /**
     * Returns the minimum or preferred dimension needed to layout the target.
     *
     * @param target the component which needs to be laid out
     * @param preferred whether to use preferred or minimum sizes
     * @return the layout size for the target container
     */
    private Dimension layoutSize(final Container target, final boolean preferred) {
        synchronized (target.getTreeLock()) {
            int targetWidth = target.getSize().width;

            if (targetWidth == 0) {
                targetWidth = Integer.MAX_VALUE;
            }

            final int hgap = getHgap();
            final int vgap = getVgap();
            final Insets insets = target.getInsets();
            final int horizontalInsetsAndGap = insets.left + insets.right + (hgap * 2);
            final int maxWidth = targetWidth - horizontalInsetsAndGap;

            final Dimension dim = new Dimension(0, 0);
            int rowWidth = 0;
            int rowHeight = 0;

            final int nmembers = target.getComponentCount();

            for (int i = 0; i < nmembers; i++) {
                final Component m = target.getComponent(i);

                if (m.isVisible()) {
                    final Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                    if (rowWidth + d.width > maxWidth) {
                        addRow(dim, rowWidth, rowHeight);
                        rowWidth = 0;
                        rowHeight = 0;
                    }

                    if (rowWidth != 0) {
                        rowWidth += hgap;
                    }

                    rowWidth += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                }
            }

            addRow(dim, rowWidth, rowHeight);

            dim.width += horizontalInsetsAndGap;
            dim.height += insets.top + insets.bottom + vgap * 2;

            final Container scrollPane = SwingUtilities.getAncestorOfClass(JScrollPane.class, target);
            if (scrollPane != null) {
                dim.width -= hgap + 1;
            }

            return dim;
        }
    }

    /**
     * Adds a new row to the given dimension.
     *
     * @param dim the dimension to add the row to
     * @param rowWidth the width of the row
     * @param rowHeight the height of the row
     */
    private void addRow(final Dimension dim, final int rowWidth, final int rowHeight) {
        dim.width = Math.max(dim.width, rowWidth);

        if (dim.height > 0) {
            dim.height += getVgap();
        }

        dim.height += rowHeight;
    }
}
