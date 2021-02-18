package obscurum;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import obscurum.display.terminal.AsciiPanel;
import obscurum.screens.MainMenuScreen;
import obscurum.screens.Screen;

public class GameDriver extends JFrame implements KeyListener {
    private final AsciiPanel terminal;
    private Screen screen;

    public GameDriver(AsciiPanel terminal, Screen screen) {
        super("Obscurum");
        this.terminal = terminal;
        this.screen = screen;

        add(this.terminal);
        pack();
        setResizable(false);
        addKeyListener(this);
        repaint();
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q && screen instanceof MainMenuScreen) {
            this.dispose();
        }
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
