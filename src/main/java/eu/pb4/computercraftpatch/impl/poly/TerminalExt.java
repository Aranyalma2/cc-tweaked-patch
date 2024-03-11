package eu.pb4.computercraftpatch.impl.poly;

import dan200.computercraft.core.terminal.Terminal;
import dan200.computercraft.shared.computer.core.ServerComputer;
import eu.pb4.computercraftpatch.mixin.ServerComputerAccessor;
import eu.pb4.mapcanvas.api.core.DrawableCanvas;

public interface TerminalExt {
    int getRenderedWidth();
    int getRenderedHeight();

    DrawableCanvas getRendered(long tick);

    static TerminalExt of(Terminal terminal) {
        return (TerminalExt) terminal;
    }

    static TerminalExt of(ServerComputer computer) {
        return of(((ServerComputerAccessor) computer).getTerminal());
    }
}
