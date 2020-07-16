package obscurum.items;

import obscurum.creatures.Creature;
import obscurum.creatures.abilities.FireBlast;
import obscurum.creatures.abilities.HealingTouch;
import obscurum.creatures.abilities.Spell;
import obscurum.display.Display;
import obscurum.display.DisplayTile;

/**
 * This models a spell tome, which can be used to teach the player a spell.
 * @author Alex Ghita
 */
public class SpellTome extends ConsumableItem {
  private Spell[] availableSpells = {new FireBlast(), new HealingTouch()};
  private Spell spell;
  private static DisplayTile[][] standardDisplay = {
    {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('_'), new DisplayTile('_'), new DisplayTile('.'),
     new DisplayTile('.'), new DisplayTile('.'), new DisplayTile('-'),
     new DisplayTile('-'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('~'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('-'), new DisplayTile('.'), new DisplayTile('_'),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('_'), new DisplayTile('.'), new DisplayTile('-'),
     new DisplayTile('~'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('~'), new DisplayTile('~'), new DisplayTile('-'),
     new DisplayTile('-'), new DisplayTile('.'), new DisplayTile('.'),
     new DisplayTile('.'), new DisplayTile('_'), new DisplayTile('_'),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' ')},
    {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile('/', Display.BROWN),
     new DisplayTile('/'), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile('\''),
     new DisplayTile('V'), new DisplayTile('\''), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('\\'),
     new DisplayTile('\\', Display.BROWN), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' ')},
    {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('/', Display.BROWN), new DisplayTile('/'),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile('|'),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('\\'),
     new DisplayTile('\\', Display.BROWN), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' ')},
    {new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('/', Display.BROWN), new DisplayTile('/'),
     new DisplayTile('_'), new DisplayTile('_'), new DisplayTile('.'),
     new DisplayTile('.'), new DisplayTile('.'), new DisplayTile('-'),
     new DisplayTile('-'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('~'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('~'), new DisplayTile('-'), new DisplayTile('.'),
     new DisplayTile('_'), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('|'), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('_'), new DisplayTile('.'), new DisplayTile('-'),
     new DisplayTile('~'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('~'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('-'), new DisplayTile('-'), new DisplayTile('.'),
     new DisplayTile('.'), new DisplayTile('.'), new DisplayTile('_'),
     new DisplayTile('_'), new DisplayTile('\\'),
     new DisplayTile('\\', Display.BROWN), new DisplayTile(' '),
     new DisplayTile(' ')},
    {new DisplayTile(' '), new DisplayTile('/', Display.BROWN),
     new DisplayTile('/'), new DisplayTile('_'),
     new DisplayTile('_'), new DisplayTile('.'), new DisplayTile('.'),
     new DisplayTile('.'), new DisplayTile('.'), new DisplayTile('.'),
     new DisplayTile('-'), new DisplayTile('-'), new DisplayTile('-'),
     new DisplayTile('-'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('~'), new DisplayTile('~'), new DisplayTile('.'),
     new DisplayTile('_'), new DisplayTile('\\'), new DisplayTile(' '),
     new DisplayTile('|'), new DisplayTile(' '), new DisplayTile('/'),
     new DisplayTile('_'), new DisplayTile('.'), new DisplayTile('~'),
     new DisplayTile('~'), new DisplayTile('~'), new DisplayTile('~'),
     new DisplayTile('-'), new DisplayTile('-'), new DisplayTile('-'),
     new DisplayTile('-'), new DisplayTile('.'), new DisplayTile('.'),
     new DisplayTile('.'), new DisplayTile('.'), new DisplayTile('.'),
     new DisplayTile('_'), new DisplayTile('_'),
     new DisplayTile('\\'),
     new DisplayTile('\\', Display.BROWN), new DisplayTile(' ')},
    {new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('\\'), new DisplayTile('\\'),
     new DisplayTile('|'), new DisplayTile('/'), new DisplayTile('/'),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN),
     new DisplayTile('=', Display.BROWN), new DisplayTile('=', Display.BROWN)},
    {new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile('\'', Display.BROWN), new DisplayTile('_', Display.BROWN),
     new DisplayTile('_', Display.BROWN), new DisplayTile('_', Display.BROWN),
     new DisplayTile('\'', Display.BROWN), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' '), new DisplayTile(' '), new DisplayTile(' '),
     new DisplayTile(' ')}
  };

  public SpellTome() {
    super("Tome of ", Display.BOOK, Display.BROWN,
        "Teaches you or improves your mastery of ", 1, 1, Item.RARE,
        standardDisplay);
    spell = availableSpells[(int)(Math.random() * availableSpells.length)];
    name += spell.getName();
    description += spell.getName() + ".";
  }

  public void use(Creature target) {
    target.learnSpell(spell);
  }
}
