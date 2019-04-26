import java.util.*;
public class Game{
   public static void main(String[] args){
      System.out.println("Welcome to the game.\n");
      Scanner console = new Scanner(System.in);
      Player me = new Player(100,10,50,1);
      System.out.println("You have "+me.hp+" health, "+me.da+" damage, "+me.ar+" armor, and "+me.mo+" coins.\n");
      while (me.hp > 0){
         System.out.println("Would you like to. . .\n\na. explore\nb. shop\nc. open inventory");
         String pap = console.next();
         if (pap.equals("a")){
            explore(console,me);
         }
         if (pap.equals("b")){
            shop(console,me);
         }
         if (pap.equals("c")){
            inventory(console,me);
         }
         if (pap.equals("console")){
            testing(console, me);
         }
      }
      if (me.zone == 7){
         System.out.println("You finish the game with "+me.maxhp+" health, "+me.da+" damage, "+me.ar+" armor, and "+me.mo+" coins.");
         System.out.println("FINAL KILL COUNT: "+me.finalkillcount);
      }
      else{
         try{
            Thread.sleep(500);
            System.out.println("\nYou died.");
            Thread.sleep(500);
            System.out.println(".");
            Thread.sleep(500);
            System.out.println(".");
            Thread.sleep(500);
            System.out.println(".");
            Thread.sleep(500);
         }
         catch(Exception e){
         }
         System.out.println("\n\nG A M E    O V E R");
      }
   }
   public static void explore(Scanner console, Player me){
      String ack = "";
      int zonemag = (int)((Math.pow(3,me.zone)/(me.zone+1)));
      String enemyname;
      Random rng = new Random();
      String[] zonelist = new String[]{"Plains","Forest","Deep Woods","Prairie","Foothills","Mountainside","Peak"};
      while (!ack.equals("n")){
         if (me.hp <= 0){
            return;
         }
         System.out.println("Location: "+zonelist[me.zone]+"\n\nWant to explore? y/n");
         if (me.killcount >= 5){
            System.out.println("(Type \"next\" to attempt to travel to the next zone.)");
         }
         ack = console.next();
         if (ack.equals("n")){
            return;
         }
         else if (ack.equals("y")){
            try{
               for (int i = 1; i <= 3; i++){
                  Thread.sleep(250);
                  System.out.print(". ");
               }
               System.out.println("\n\n");
            } 
            catch (Exception e){
            }
            int random = rng.nextInt(6);
            if (random >= 2){
               Chr monster = new Chr((50-rng.nextInt(16))*zonemag,(8+rng.nextInt(5))*zonemag,(40-rng.nextInt(5))*zonemag, (3-rng.nextInt(6))*zonemag);
               enemyname = choosename(me, monster, rng);
               combat(me, rng, console, monster, enemyname);
            }
            if (random == 1){
               discover(me, rng, console, zonemag);
            }
            if (random == 0){
               int randomcoins = (rng.nextInt(30)+21)*(zonemag);
               me.giveMo(randomcoins);
               System.out.println("You found a stash of " + randomcoins+" coins!\nYou now have " + me.mo + " coins.");
            }
         }
         else if (ack.equals("next")){
            if (me.zone < 6){
               System.out.println("A large monster blocks the way to the " + zonelist[me.zone + 1]);
               Chr boss = new Chr((250)*(zonemag),(20)*(zonemag),(200)*(zonemag),(3)*(zonemag));
               combat(me, rng, console, boss, "Gatekeeper");
               if (me.hp > 0 && boss.hp == 0){
                  me.zone +=1;
                  System.out.println("You travel to the " +zonelist[me.zone]);
                  me.killcount = 0;
                  return;
               }
               if (me.hp <= 0){
                  System.out.println("\nThe guardian yeets your head off.\n");
               }
            }
            else{
               System.out.println("The final boss stands before you!");
               Chr finalboss = new Chr(100000, 1000, 100000, 500);
               combat(me, rng, console, finalboss, "Feffrey Jetters");
               if (me.hp > 0 && finalboss.hp == 0){
                  System.out.println("You won the game!");
                  me.hp = 0;
                  me.zone = 7;
                  return;
               }
               if (me.hp <= 0){
                  System.out.println("\nYou aren't wetters like Feffrey Jetters.");
               }
            }
         }
      }
   }
   public static void discover(Player me, Random rng, Scanner console, int zonemag){
      String discovery = "";
      if (me.zone == 0){
         discovery = "an old campground.";
      }
      if (me.zone == 1){
         discovery = "an abandoned cabin.";
      }
      if (me.zone == 2){
         discovery = "an altar.";
      }
      if (me.zone == 3){
         discovery = "some ancient ruins.";
      }
      if (me.zone == 4){
         discovery = "a dusty mineshaft.";
      }
      if (me.zone == 5){
         discovery = "a dreary cave.";
      }
      if (me.zone == 6){
         discovery = "Bleecker's room.";
      }
      System.out.println("You come across "+discovery+" Want to explore? y/n\n");
      String choice = console.next();
      if (choice.equals("y")){
         try{
            for (int i = 1; i <= 3; i++){
               Thread.sleep(250);
               System.out.print(". ");
            }
            System.out.println("\n\n");
         } 
         catch (Exception e){
         }
         int random = 0;
         if (me.potions < me.maxBag){
            random = rng.nextInt(5);
         }
         else{
            random = rng.nextInt(4);
         }
         if (random == 0){
            int money = (rng.nextInt(50) + 21)*zonemag;
            System.out.println("You find an old sack of coins. It contained "+money+" coins.\n");
            me.giveMo(money); 
         }
         if (random == 1){
            me.da = (int)(me.da*1.2);
            System.out.println("You find an upgrade to your weapon, your damage is now " +me.da+"\n");
         }
         if (random == 2){
            me.ar = me.ar + ((int)(2*zonemag));
            System.out.println("You found an upgrade to your armor, your armor is now " +me.ar+"\n");
         }
         if (random == 3){
            Chr monster = new Chr((50-rng.nextInt(16))*zonemag,(8+rng.nextInt(5))*zonemag,(100-rng.nextInt(11))*zonemag, (3-rng.nextInt(6))*zonemag);
            String enemyname = choosename(me, monster, rng);
            System.out.println("You were ambushed! The "+enemyname+" has already looted the area!\n");
            combat(me, rng, console, monster, enemyname); 
         }
         if (random == 4){
            me.potions = me.maxBag;
            System.out.println("You found a bag of potions. You now have "+me.potions+"/"+me.maxBag+" potions.");
         }
      }
      else{
         return;
      }
   }
   public static void shop(Scanner console, Player me){
      int healthrestore = me.maxhp/2;
      System.out.println("a. Increase damage by "+me.damagemag+" for "+me.damagecost+" coins. \nb. Buy a potion that restores 50% of your health ("+healthrestore+") for "+((me.zone+1)*10)+" coins\nc. Increase maximum health by "+me.maxhpmag+" for "+me.maxhpcost+" coins.\nd. Increase armor by "+me.armormag+" for "+me.armorcost+" coins.\ne. Upgrade inventory capacity by 1 for "+me.bagcost+"\n\nBalance: " + me.mo + "\nz - exit");
      String ackle = console.next();
      while (!ackle.equals("z")){
         if (ackle.equals("a")){
            if(me.mo >= me.damagecost){
               me.giveMo(-me.damagecost);
               me.buff(me.damagemag);
               System.out.println("Your damage is now " + me.da+"\n");
               me.damagecost = (int)(me.damagecost * 1.3);
               me.damagemag = (int)(me.damagemag * 1.25);
            }
            else{
               System.out.println("Insufficient funds.\n");
            }
         }
         if (ackle.equals("b")){
            if(me.mo >= ((me.zone+1)*10)){
               if (me.potions < me.maxBag){
                  me.potions += 1;
                  System.out.println("You now have "+me.potions+"/"+me.maxBag+" potions\n");
                  me.giveMo(-((me.zone+1)*10));
               }
               else{
                  System.out.println("Your inventory is full!\n");
               }
            }
            else{
               System.out.println("Insufficient funds.\n");
            }
         }
         if (ackle.equals("c")){
            if(me.mo >= me.maxhpcost){
               me.giveMo(-me.maxhpcost);
               me.giveMaxhp(me.maxhpmag);
               System.out.println("Your max health is now " + me.maxhp+"\n");
               me.maxhpcost = (int)(me.maxhpcost * 1.3);
               me.maxhpmag = (int)(me.maxhpmag * 1.25);
               healthrestore = (me.maxhp/2);
            }
            else{
               System.out.println("Insufficent funds.\n");
            }
         }
         if (ackle.equals("d")){
            if(me.mo >= me.armorcost){
               me.giveMo(-me.armorcost);
               me.giveAr(me.armormag);
               System.out.println("Your now have " + me.ar + " armor.\n");
               me.armorcost = (int)(me.armorcost * 4);
               me.armormag = (int)(me.armormag * 3);
            }
            else{
               System.out.println("Insufficent funds.\n");
            }
         }
         if (ackle.equals("e")){
            if(me.mo >= me.bagcost){
               me.giveMo(-me.bagcost);
               me.maxBag += 1;
               System.out.println("You now have " + me.maxBag + " inventory slots.\n");
               me.bagcost = (int)(me.bagcost * 2);
            }
            else{
               System.out.println("Insufficent funds.\n");
            }
         }
         System.out.println("a. Increase damage by "+me.damagemag+" for "+me.damagecost+" coins. \nb. Buy a potion that restores 50% of your health ("+healthrestore+") for "+((me.zone+1)*10)+" coins\nc. Increase maximum health by "+me.maxhpmag+" for "+me.maxhpcost+" coins.\nd. Increase armor by "+me.armormag+" for "+me.armorcost+" coins.\ne. Upgrade inventory capacity by 1 for "+me.bagcost+"\n\nBalance: " + me.mo + "\nz - exit");
         ackle = console.next();
      }
   }
   public static void combat(Player me, Random rng, Scanner console, Chr monster, String enemyname){ 
      System.out.println("You got attacked by a "+enemyname+"! \n"+enemyname.toUpperCase()+"\nHP: " + monster.hp + "/"+monster.maxhp+"   DMG: " + monster.da + "\nYOU\nHP: " + me.hp + "/"+me.maxhp+"  DMG: " + me.da);
      if (monster.ar > 0){
         System.out.println("The "+enemyname+" is armored.");
      }
      if (monster.ar < 0){
         System.out.println("The "+enemyname+" is weak.");
      }
      while (monster.hp > 0){
         System.out.println("Would you like to: \na. Fight\nb. Flee\nc. Open inventory");
         String combatchoice = console.next();
         if (combatchoice.equals("a")){
            try{
               Thread.sleep(250);
               System.out.println();                 
               if (rng.nextInt(8) == 0){
                  monster.attackedBy(me);
                  monster.attackedBy(me);
                  System.out.println("Critical hit! You did " + ((me.da - monster.ar)*2) + " damage.\n");
               }
               else{
                  monster.attackedBy(me);
                  System.out.println("You did " + (me.da - monster.ar) + " damage.\n");
               }
               Thread.sleep(250);
               if (rng.nextInt(8) == 0){
                  me.attackedBy(monster);
                  me.attackedBy(monster);
                  System.out.println("Critical hit! The "+enemyname+" did " + ((monster.da - me.ar)*2) + " damage.\n");
               }
               else{
                  me.attackedBy(monster);
                  System.out.println("The "+enemyname+" did " + (monster.da - me.ar) + " damage.\n");
               }
               Thread.sleep(250);
            }
            catch (Exception e){
            }
            System.out.println(enemyname.toUpperCase()+"\nHP: " + monster.hp + "/"+monster.maxhp+"  DMG: " + monster.da + "\nYOU\nHP: " + me.hp + "/"+me.maxhp+"  DMG: " + me.da);
            if (monster.hp <= 0){
               System.out.println("The "+enemyname+ " had " + monster.mo + " coins. You now have " + me.mo +" coins.\n");
            }
         }
         if (combatchoice.equals("b")){
            try{
               for (int i = 1; i <= 3; i++){
                  Thread.sleep(250);
                  System.out.print(". ");
               }
               System.out.println("\n\n");
            }  
            catch (Exception e){
            }
            if (rng.nextInt(3) == 0){
               me.attackedBy(monster);
               System.out.println("You failed to flee. The "+enemyname+" did " + monster.da + " damage to you.\nYou now have " +me.hp+ " health.");
            }
            else{
               System.out.println("You successfully escaped.");
               return;
            }
         }
         if (combatchoice.equals("c")){
            inventory(console, me);
         }
         if (me.hp <= 0){
            return;
         }
      }
   }
   public static String choosename(Player me, Chr monster, Random rng){
      String[] enemynamelistplains = new String[]{"Coyote","Pickpocket","Bandit","Wild Beast","AquaNibba","Badger","Mean Rabbit","Ferret"};
      String[] enemynamelistforest = new String[]{"Bull Elk","Thief","Black Bear","Hog","Wolf","Deer","Sloth"};
      String[] enemynamelistdeepwoods = new String[]{"Spiky Bush","Manged Wolf","Diseased Boar","Sickly Snake","Leper","Brown Bear","Red Fox","Poisonous Frog","Monkey"};
      String[] enemynamelistprairie = new String[]{"Vulture","Cloaked Assassin","Rabid Bear","Bandit Chief","Bison","White Owl","Condor"};
      String[] enemynamelistfoothills = new String[]{"Hawk","Ram","Swarm of Vultures","Eagle","Mountain Goat","Falcon","Swole Turkey"};
      String[] enemynamelistmountainside = new String[]{"Mysterious Traveler","Bald Eagle","Lost Knight","Hitman","Bounty Hunter","Cougar","Reindeer","Yak"};
      String[] enemynamelistpeak = new String[]{"Slowie Cloyer","Argay","Bordan Jannish","Luanne","Noiman","Kevin Dirkala","Scoliosis Monster","Liberty Football Recruiter"};
      int[] randomnamelength = new int[]{enemynamelistplains.length,enemynamelistforest.length,enemynamelistdeepwoods.length,enemynamelistprairie.length,enemynamelistfoothills.length,enemynamelistmountainside.length,enemynamelistpeak.length};
      int choice = rng.nextInt(randomnamelength[me.zone]);
      if (me.zone == 0){
         return enemynamelistplains[choice];
      }
      if (me.zone == 1){
         return enemynamelistforest[choice];
      }
      if (me.zone == 2){
         return enemynamelistdeepwoods[choice];
      }
      if (me.zone == 3){
         return enemynamelistprairie[choice];
      }
      if (me.zone == 4){
         return enemynamelistfoothills[choice];
      }
      if (me.zone == 5){
         return enemynamelistmountainside[choice];
      }
      if (me.zone == 6){
         return enemynamelistpeak[choice];
      }
      return "Error";
   }
   public static void inventory(Scanner console, Player me){
      System.out.println("a. Use Potion "+me.potions+"/"+me.maxBag+" (+"+me.maxhp/2+" hp)\nz. exit");
      String choice = "";
      while (!choice.equals("z")){
         choice = console.next();
         if (choice.equals("a")){
            if (me.potions == 0){
               System.out.println("You do not have any potions.");
            }
            else{
               if (me.hp == me.maxhp){
                  System.out.println("You already are at max health.");
               }
               else{
                  me.giveHp(me.maxhp/2);
                  me.potions -= 1;
                  if (me.hp > me.maxhp){
                     me.hp = me.maxhp;
                  }
                  System.out.println("You used a potion. Your health is now ("+me.hp+"/"+me.maxhp+")");
               }
            }
         }
         System.out.println("a. Use Potion "+me.potions+"/"+me.maxBag+" (+"+me.maxhp/2+" hp)\nz. exit");   
      }
   }
   public static void testing(Scanner console, Player me){
      System.out.println("Player ints: hp, maxhp, da, mo, ar, zone, killcount, finalkillcount, damagecost, damagemag, maxhpcost, maxhpmag, armorcost, armormag, potions");
      String cheatchoice = console.next();
      int magnitude;
      if (cheatchoice.equals("hp")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.hp = magnitude;
      }
      if (cheatchoice.equals("maxhp")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.maxhp = magnitude;
      }
      if (cheatchoice.equals("da")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.da = magnitude;
      }
      if (cheatchoice.equals("mo")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.mo = magnitude;
      }
      if (cheatchoice.equals("ar")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.ar = magnitude;
      }
      if (cheatchoice.equals("zone")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.zone = magnitude;
      }
      if (cheatchoice.equals("killcount")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.killcount = magnitude;
      }
      if (cheatchoice.equals("finalkillcount")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.finalkillcount = magnitude;
      }
      if (cheatchoice.equals("damagecost")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.damagecost = magnitude;
      }
      if (cheatchoice.equals("damagemag")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.damagemag = magnitude;
      }
      if (cheatchoice.equals("maxhpcost")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.maxhpcost = magnitude;
      }
      if (cheatchoice.equals("maxhpmag")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.maxhpmag = magnitude;
      }
      if (cheatchoice.equals("armorcost")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.armorcost = magnitude;
      }
      if (cheatchoice.equals("armormag")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.armormag = magnitude;
      }
      if (cheatchoice.equals("potions")){
         System.out.println("magnitude:");
         magnitude = console.nextInt();
         me.potions = magnitude;
      }
   }
   
}
