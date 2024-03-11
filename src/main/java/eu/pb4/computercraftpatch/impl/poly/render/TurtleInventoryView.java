package eu.pb4.computercraftpatch.impl.poly.render;

import dan200.computercraft.shared.turtle.inventory.TurtleMenu;
import eu.pb4.computercraftpatch.impl.poly.gui.ComputerGui;
import eu.pb4.computercraftpatch.impl.poly.gui.TurtleInventoryGui;
import eu.pb4.mapcanvas.api.core.CanvasColor;
import eu.pb4.mapcanvas.api.core.DrawableCanvas;
import eu.pb4.mapcanvas.api.font.DefaultFonts;
import eu.pb4.mapcanvas.api.utils.CanvasUtils;
import net.minecraft.inventory.Inventory;
import xyz.nucleoid.server.translations.api.Localization;

public class TurtleInventoryView extends ScreenElement {
    private final ComputerGui gui;
    private final TurtleMenu wrapped;
    private final Inventory inventory;
    //private final Snowball[] ui;

    public TurtleInventoryView(int x, int y, ComputerGui gui, TurtleMenu turtleMenu) {
        super(x, y);
        this.gui = gui;
        this.wrapped = turtleMenu;
        this.inventory = turtleMenu.slots.get(0).inventory;

        /*this.ui = new Snowball[turtle.getContainerSize()];

        for (int i = 0; i < turtle.getContainerSize(); i++) {
            this.ui[i] = new Snowball(EntityType.SNOWBALL, FakeWorld.INSTANCE);
            this.ui[i].setPos(Vec3.atCenterOf(gui.pos).add(-2 - (i % 4) / 4d, 0 + (i / 4) / 4d, 1));
            this.ui[i].setItem(turtle.getItem(i).copy());
            this.ui[i].setNoGravity(true);
            this.gui.additionalEntities.add(this.ui[i].getId());
            gui.getPlayer().connection.send(this.ui[i].getAddEntityPacket());
        }*/
    }

    @Override
    public void render(DrawableCanvas canvas, long tick, int mouseX, int mouseY) {

        CanvasUtils.fill(canvas, this.x, this.y, this.x + this.width(), this.y + this.height(), CanvasColor.BLACK_HIGH);

        //DefaultFonts.VANILLA.drawText(canvas, "Inventory: [OPEN]", this.x, this.y, 8, CanvasColor.WHITE_HIGH);
        for (int i = 0; i < this.inventory.size(); i++) {
            var item = this.inventory.getStack(i);

            /*if (!this.ui[i].getItem().equals(item)) {
                this.ui[i].setItem(item);
                gui.getPlayer().connection.send(new ClientboundSetEntityDataPacket(this.ui[i].getId(), this.ui[i].getEntityData(), false));
            }*/

            String text;
            CanvasColor canvasColor;

            var selected = this.wrapped.getSelectedSlot() == i;

            if (item.isEmpty()) {
                text = "[Empty]";
                canvasColor = this.wrapped.getSelectedSlot() == i ? CanvasColor.YELLOW_NORMAL : CanvasColor.WHITE_GRAY_HIGH;
            } else {
                var name = Localization.text(item.getName(), this.gui.getPlayer()).getString();

                if (name.length() > 18) {
                    var delta = ((this.gui.getPlayer().age / 10) % (name.length() - 18));

                    name = name.substring(delta, 18 + delta);
                }

                text = item.getCount() + " × " + name + "";//item.getItemHolder().unwrapKey().get().location();
                canvasColor = selected ? CanvasColor.YELLOW_HIGH :CanvasColor.WHITE_HIGH;
            }

            DefaultFonts.VANILLA.drawText(canvas, text, this.x + 10, this.y + i * 9, 8, canvasColor);
            if (selected) {
                DefaultFonts.VANILLA.drawText(canvas, "»", this.x + 1 + (this.gui.getPlayer().age / 10) % 2, this.y + i * 9, 8, canvasColor);
            }
        }
    }

    @Override
    public int width() {
        return 128;
    }

    @Override
    public int height() {
        return 145;
    }

    @Override
    public void click(int x, int y, ClickType type) {
        if (ScreenElement.isIn(x, y, 0, 0,  128, 149)) {
            this.gui.close();
            new TurtleInventoryGui(this.gui.getPlayer(), this.wrapped);
        }
    }
}
