import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Game {

  public static void main(String[] args) {

    ArrayList<Hero> party = new ArrayList<Hero>();
    greet(); //greet the user
    buildParty(party);
    line();
    System.out.println("You have assembled your party! They are: ");
    printParty(party);
    line();
    Boss boss = new Boss();
    System.out.print("Are you ready to slay it? (Yes or No): ");
    boolean start = getAffirmation();
    if (start) {
      line();
      System.out.println("You command your party to approach. The battle begins!");
      playGame(party, boss);
    } else {
      System.out.println("Your party looks disappointed as you unsummon them...");
      System.out.println("\"Coward,\" mutters one of them as they fade from your sight.");
    }//if-else to start the Game
    line();
  }//main

  private static void line() {
    System.out.println("----------------------------------------------------------------");
  }//a cosmetic line used frequently

  private static void greet() {
    System.out.println("Welcome to the Boss Slaying Game!");
    System.out.println("First, select which heroes you desire (one of each, up to four):");
    line();
    System.out.println("[1] Uzko the Knight");
    System.out.println("[2] Heshi the Ranger");
    System.out.println("[3] Drezzero the Mage");
    System.out.println("[4] Idrez the Cleric");
    System.out.println("[5] Undilo the Bard");
    line();
  }//method to greet the user

  private static void buildParty(ArrayList<Hero> p) {
    Scanner keyboard = new Scanner(System.in);
    int i = 0;
    while(true) {
      System.out.print("Select hero #" + (i+1) + ": ");
      int input = keyboard.nextInt();
      if (input < 1 || input > 5) {
        //if the user entered a number too low or too high
        System.out.println("Pick a hero 1-5!");
      } else {
        //user entered a number 1-5
        boolean successful = true; //flag for whether or not input works
        for (int j = 0; j < p.size(); j++) {
          if (p.get(j).getID() == (input-1)) {
            successful = false;
          }//if the ID they entered is already in the party, fail the input
        }//iterate through the party
        if (successful) {
          p.add(new Hero(input-1));
          i++;
        } else {
          System.out.println("That hero is already in your party!");
        }//if-else for successful input
      }//if-else for picking 1-5
      if (i == 4) {
        break;
      }//terminal condition
    }//while loop
  }//method to build the party ArrayList

  private static void printParty(ArrayList<Hero> p) {
    for(int i = 0; i < p.size(); i++) {
      Hero current = p.get(i);
      System.out.println(current.getName() + ": (" + current.getHP() + "/"
                          + current.getMAXHP() + ")");
    }//iterate through the party
  }//print the party contents

  private static boolean getAffirmation() {
    Scanner keyboard = new Scanner(System.in);
    String input = keyboard.nextLine().toLowerCase();
    if(input.charAt(0) == 'y') {
      return true;
    } else {
      return false;
    }//if-else
  }//returns a true if the user types "yes" or "y", and false if anything else

  private static void playGame(ArrayList<Hero> party, Boss boss) {
    boolean playerRound = true;
    Random gen = new Random();
    while(party.size() > 0 && boss.getHP() > 0) {
      if (playerRound) {
        line();
        System.out.println("Player Turn");
        line();
        System.out.println(boss.getName() + ": (" + boss.getHP() + "/" + boss.getMAXHP() + ")");
        System.out.println("What would you like to do?");
        System.out.print("Attack, Heal, Flee, Status, or Quit: ");
        char choice = getChoice();
        if(choice == 'a') {
          int attack = getAttackSum(party, boss);
          boss.changeHP(-1*attack);
        } else if(choice == 'h') {
          int healing = getHealSum(party);
          healParty(party, healing);
        } else if(choice == 'f') {
          System.out.print("You command the party to flee ");
          if(flee()) {
            //successful Flee
            System.out.println("and they successfully escape.");
            System.out.println("Your party looks disappointed as you unsummon them...");
            System.out.println("\"Coward,\" mutters one of them as they fade from your sight.");
            break;
          } else {
            System.out.println("but " + boss.getName() + " will not let them go so easily!");
          }
        } else if(choice == 'q') {
          System.out.println("Your party and the boss vanish before your eyes.");
          System.out.println("You wake up in your bed... What kind of dream was that?!");
          break;
        } else if (choice == 's') {
           printParty(party);
        } else {
          System.out.println("The party does not recognize that command...");
          System.out.println("The party fumbles without your command!");
        }
        if (choice != 's') {
          playerRound = false;
        }//if the player checks their status, the turn should not switch
      } else {
        line();
        System.out.println(boss.getName() + "\'s Turn");
        line();
        double roll = gen.nextDouble();
        boolean stunPlayer = false;
        if(roll > 0.75) { //percentage chance to do special attack
          switch(boss.getID()) {
            case 0: System.out.println(boss.getName() + " creates a horrifying mirage that entrances the heroes.");
                    System.out.println("The heroes claw at themselves in horror!");
                    attackAll(party, gen.nextInt(10));
                    stunPlayer = gen.nextBoolean();
                    break;
            case 1: System.out.println(boss.getName() + " shrieks and stuns the heroes.");
                    System.out.println("The heroes are frozen with fright!");
                    stunPlayer = true; //skip the player's next turn with a stun
                    break;
            case 2: int target = gen.nextInt(party.size()); //pick a target
                    System.out.println(boss.getName() + " strikes at the soul of " + party.get(target).getName());
                    party.get(target).changeMAXHP(-1*(party.get(target).getMAXHP()/2));
                    if (party.get(target).getHP() > party.get(target).getMAXHP()) {
                      party.get(target).setHP(party.get(target).getMAXHP());
                    }//reduce hp to max if hp is greater than max
                    break;
            default: bossAttack(party, boss);
                     break;
          }
        } else {
          bossAttack(party, boss);
        }// if-else for special attack
        if(!stunPlayer) {
          playerRound = true;
        } else {
          stunPlayer = false;
        }//if the player isn't stunned, pass turn
      }//if-else to determine turns
    }//loop that runs the game
  }//method to actually play the game

  private static char getChoice() {
    Scanner keyboard = new Scanner(System.in);
    String input = keyboard.nextLine().toLowerCase();
    return input.charAt(0);
  }//returns a character that represents the player's choices (attack, heal, flee, quit)
  //based on the first letter of the player's choices

  private static int getAttackSum(ArrayList<Hero> party, Boss boss) {
    int total = 0;
    for (int i = 0; i < party.size(); i++) {
      int attack = party.get(i).getAttack();
      if (boss.getID() == 3 && (party.get(i).getID() == 0 || party.get(i).getID() == 1
                       || party.get(i).getID() == 4)) {
        //if the boss is the Iron Harlequin, reduce damage from all physical sources
        attack = (attack/3)-1;
      }//if-condition for Iron Harlequin boss
      if (boss.getID() == 4 && (party.get(i).getID() == 2 || party.get(i).getID() == 3
                       || party.get(i).getID() > 4 || party.get(i).getID() < 0)) {
        //if the boss is Smogtooth, damage immune from all magical sources
        attack = 0;
      }//if-condition for Iron Harlequin boss
      total += attack;
    }//iterate through party and add their attacks to my sum
    return total;
  }//get the attacking sum of the characters

  private static int getHealSum(ArrayList<Hero> party) {
    int total = 0;
    for (int i = 0; i < party.size(); i++) {
      if(party.get(i).canHeal()) {
        total += party.get(i).getHeal();
      }//if the party member can heal, do so.
    }//iterate through party and add their healing if possible
    return total;
  }//get the attacking sum of the characters

  private static void healParty(ArrayList<Hero> party, int healAmount) {
    for (int i = 0; i < party.size(); i++) {
      party.get(i).changeHP(healAmount);
    }//iterate through the party and apply their health
    for (int i = 0; i < party.size(); i++) {
      if(party.get(i).getHP() > party.get(i).getMAXHP()) {
        party.get(i).setHP(party.get(i).getMAXHP());
      }//prevents party members from healing beyond maximum health values
    }//iterate through the party and apply their health
  }//heal the party for the given healing value

  private static boolean flee() {
    Random gen = new Random();
    double roll = gen.nextDouble();
    if (roll >= 0.85) {
      //roll to try to escape, 15% success rate
      return true;
    } else {
      return false;
    }//if-else for rolling
  }//method to try to flee, returns true if success and false if fail

  private static void bossAttack(ArrayList<Hero> party, Boss boss) {
    Random gen = new Random();
    int roll = gen.nextInt(8);
    if (boss.getID() == 1) {
      //The Eternal Shrieker attacks everyone, always. Can add more
      System.out.println(boss.getName() + " attacks everyone!");
      attackAll(party, boss.getAttack());
    } else if (roll == 0) {
      //all bosses have a 1/5 chance to attack everyone
      System.out.println(boss.getName() + " attacks everyone!");
      attackAll(party, boss.getAttack());
    } else {
      int target = gen.nextInt(party.size());
      System.out.println(boss.getName() + " attacks " + party.get(target).getName());
      party.get(target).changeHP(-1*(boss.getAttack()));
    }//if-elif structure for choosing target(s)
    for (int i = 0; i < party.size(); i++) {
      if(party.get(i).getHP() <= 0) {
        party.remove(i);
        i = 0;
      }//kill the heroes with 0 or less HP and reset i for checking the
    }//iterate through heroes, check to see if they are dead, and kill them
    if(party.size() > 0) {
      if(party.get(0).getHP() <= 0) {
        party.clear();
      }//cleans up last remaining dead party member with negative hp
    }//ensures the party isn't empty
  }//method to generate the boss's attack and target, then apply the hit

  private static void attackAll(ArrayList<Hero> party, int damage) {
    for (int i = 0; i < party.size(); i++) {
      party.get(i).changeHP(-1*damage);
    }//iterate through party and apply damage to all players
  }//apply damage to all players

}//class
