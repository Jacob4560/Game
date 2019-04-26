public class Player{ 
   int hp;
   int maxhp;
   int da;
   int mo;
   int ar;
   
   int zone;
   int killcount = 0;
   int finalkillcount = 0; 
     
   int damagecost = 20;
   int damagemag = 5;
   
   int maxhpcost = 20;
   int maxhpmag = 10;
   
   int armorcost = 20;
   int armormag = 1;
   
   int potions = 0;
   int bagcost = 200;
   int maxBag = 3;
   
public Player(int hp,int da,int mo, int ar){
      this.hp = hp;
      this.maxhp = hp;
      this.da = da;
      this.mo = mo;
      this.ar = ar;
      } 
   void attackedBy(Chr attacker){
      if (attacker.da-ar >= 0){
         hp = hp - (attacker.da-ar);
         }
      if (this.hp < 0){
         this.hp = 0;
         }
      }
   void buff(int value){
      da = da + value;
      }
   void giveMo(int value){
      mo = mo + value;
      }
   void giveHp(int value){
      hp = hp + value; 
      }
   void giveMaxhp(int value){
      maxhp = maxhp + value; 
      }
   void giveAr(int value){
      ar = ar + value; 
      }   
}