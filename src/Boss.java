import java.util.Random;

public class Boss {

  private int hp; //int for tracking boss health
  private int maxHP;
  private int str; //int for tracking boss strength
  private int boss_id; //int for identifying which boss
  private String name; //boss's name
  private final String[] names = { "Horrorhood", "The Eternal Shrieker",
    "Blightstrike", "The Iron Harlequin", "Smogtooth"};
    //List of possible boss names

  public Boss() {
    Random gen = new Random();
    boss_id = gen.nextInt(names.length);
    //generates a boss id associated with the names from the above list of names

    switch (boss_id) {
      case 0: name = names[boss_id];
              hp = 150;
              str = 15;
              break;
      case 1: name = names[boss_id];
              hp = 100;
              str = 5;
              break;
      case 2: name = names[boss_id];
              hp = 150;
              str = 20;
              break;
      case 3: name = names[boss_id];
              hp = 200;
              str = 10;
              break;
      case 4: name = names[boss_id];
              hp = 150;
              str = 15;
              break;
      default: name = "mIssiNG nO.";
               hp = 1;
               str = 1;
               break;
    }//switch for boss ID's
    maxHP = hp;
    System.out.println(name + " is nearby...");
  }//constructor

  public int getHP() {
    return hp;
  } //get hp

  public int getMAXHP() {
    return maxHP;
  } //get mixumum health

  public int getSTR() {
    return str;
  } //get strength

  public String getName() {
    return name;
  } //get name

  public int getID() {
    return boss_id;
  }

  public void changeHP(int x) {
    hp += x;
  } //adds x to the hp

  public int getAttack() {
    Random gen = new Random();
    int attack = gen.nextInt(str)+10;
    if (attack > 0) {
      System.out.println(name + " swings for " + attack + " damage!");
    } else {
      System.out.println(name + " misses!");
    }//if-else for a miss or not
    return attack;
  } //get the attack of the boss

}//class
