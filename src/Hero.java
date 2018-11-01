import java.util.Random;

public class Hero {

  private int hp; //health
  private int maxHP; //max health
  private int str; //strength
  private int intl; //intelligence
  private String name;
  private int id; //the hero ID

  public Hero(int id) {
    this.id = id;
    //sets the user ID to passed in value
    switch(id) {
      case 0: name = "Uzko the Knight";
              hp = 60;
              str = 15;
              intl = 0;
              break;
      case 1: name = "Heshi the Ranger";
              hp = 40;
              str = 20;
              intl = 0;
              break;
      case 2: name = "Drezzero the Mage";
              hp = 30;
              str = 5;
              intl = 20;
              break;
      case 3: name = "Idrez the Cleric";
              hp = 30;
              str = 0;
              intl = 15;
              break;
      case 4: name = "Undilo the Bard";
              hp = 25;
              str = 15;
              intl = 10;
              break;
      default: name = "nUll ThE gLiTCh";
               hp = 1;
               str = 1;
               intl = 1;
               break;
    }//switch for setting which hero the object represents, with a default glitch character
    maxHP = hp;
    System.out.println(name + " has been summoned!");
  }//hero constructor

  public int getHP() {
    return hp;
  } //get hp

  public int getSTR() {
    return str;
  } //get strength

  public int getINT() {
    return intl;
  } //get intelligence

  public int getMAXHP() {
    return maxHP;
  } //get mixumum health

  public String getName() {
    return name;
  } //get name

  public int getID() {
    return id;
  }//return player ID

  public void setHP(int x) {
    hp = x;
  }//set current HP

  public void changeMAXHP(int x) {
    maxHP += x;
  } //adds x to the max hp

  public void changeHP(int x) {
    hp += x;
  } //adds x to the hp

  public int getAttack() {
    Random gen = new Random();
    int attack = -1;
    if (id == 0 || id == 1 || id == 4) {
      //if the hero is a strength attacker,
      attack = gen.nextInt(str);
    } else {
      //if the hero is a magic attacker,
      attack = gen.nextInt(intl);
    } //if-else
    if (attack > 0) {
      System.out.println(name + " swings for " + attack + " damage!");
    } else {
      System.out.println(name + " misses!");
    }//if-else for a miss or not
    return attack;
  } //get the attack of the given hero

  public boolean canHeal() {
    if (id == 3 || id == 4) {
      return true;
    } else {
      return false;
    }//if-else
  }//returns whether or not the hero is a healer or not

  public int getHeal() {
    Random gen = new Random();
    int heal = 0;
    if (canHeal()) {
      heal = gen.nextInt(intl);
      System.out.println(name + " heals the party for " + heal + " health!");
      return heal;
    }//if they are a healer, return a random value to heal the party
    return 0;
  }//heals (or doesn't)

}//hero class
