public class Chr{
   int hp;
   int maxhp;
   int da;
   int mo;
   int ar;
   public Chr(int hp,int da,int mo, int ar){
      this.hp = hp;
      this.maxhp = hp;
      this.da = da;
      this.mo = mo;
      this.ar = ar;
      } 
   void attackedBy(Player attacker){
      if (attacker.da-ar >= 0){
      hp = hp - (attacker.da-ar);
      }
      if (hp <= 0){
         this.hp = 0;
         attacker.giveMo(mo);
         attacker.killcount += 1;
         attacker.finalkillcount += 1;
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
}