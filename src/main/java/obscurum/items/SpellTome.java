package obscurum.items;

import java.lang.Math;
import obscurum.creatures.Creature;
import obscurum.creatures.abilities.*;
import obscurum.display.Display;
import obscurum.display.DisplayCharacter;
import obscurum.display.DisplayTile;

/**
 * This models a spell tome, which can be used to teach the player a spell.
 * @author Alex Ghita
 */
public class SpellTome extends ConsumableItem {
  private Spell[] availableSpells = {new FireBlast(), new HealingTouch()};
  private Spell spell;
  private static DisplayTile[][] standardDisplay = {
    {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('-')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('_')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('-')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('-')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('_')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' '))},
    {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of('/'), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('/')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of('\'')),
     new DisplayTile(DisplayCharacter.of('V')), new DisplayTile(DisplayCharacter.of('\'')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('\\')),
     new DisplayTile(DisplayCharacter.of('\\'), Display.BROWN), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' '))},
    {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('/'), Display.BROWN), new DisplayTile(DisplayCharacter.of('/')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of('|')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('\\')),
     new DisplayTile(DisplayCharacter.of('\\'), Display.BROWN), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' '))},
    {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('/'), Display.BROWN), new DisplayTile(DisplayCharacter.of('/')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('-')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('|')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('-')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('_')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('\\')),
     new DisplayTile(DisplayCharacter.of('\\'), Display.BROWN), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' '))},
    {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of('/'), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('/')), new DisplayTile(DisplayCharacter.of('_')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('\\')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('|')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of('/')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')), new DisplayTile(DisplayCharacter.of('~')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('-')),
     new DisplayTile(DisplayCharacter.of('-')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')), new DisplayTile(DisplayCharacter.of('.')),
     new DisplayTile(DisplayCharacter.of('_')), new DisplayTile(DisplayCharacter.of('_')),
     new DisplayTile(DisplayCharacter.of('\\')),
     new DisplayTile(DisplayCharacter.of('\\'), Display.BROWN), new DisplayTile(DisplayCharacter.of(' '))},
    {new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('\\')), new DisplayTile(DisplayCharacter.of('\\')),
     new DisplayTile(DisplayCharacter.of('|')), new DisplayTile(DisplayCharacter.of('/')), new DisplayTile(DisplayCharacter.of('/')),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('='), Display.BROWN), new DisplayTile(DisplayCharacter.of('='), Display.BROWN)},
    {new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of('\''), Display.BROWN), new DisplayTile(DisplayCharacter.of('_'), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('_'), Display.BROWN), new DisplayTile(DisplayCharacter.of('_'), Display.BROWN),
     new DisplayTile(DisplayCharacter.of('\''), Display.BROWN), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')), new DisplayTile(DisplayCharacter.of(' ')),
     new DisplayTile(DisplayCharacter.of(' '))}
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
