/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poketac;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import modelo.Battle;
import modelo.Trainer;
import modelo.Pokemon;
import modelo.Movement;
import modelo.PokeInfo;
import modelo.PokeType;
import java.io.*;

/**
 *
 * @author DiegoAndres
 */
public class PokeTAC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Bienvenido a PokeTAC! =D");
        
        PokeTAC pokeTAC = new PokeTAC();
        
        //GUI: Pantalla de bienvenida
        
        pokeTAC.initGame("Perejil");
             
        //GUI: Iniciar Batalla
        do 
        {
            pokeTAC.selectAITeam();
            
            //GUI: Seleccionar Team de usuario para la batalla
            pokeTAC.askUserTeam();

            pokeTAC.initBattle();
            
            //GUI: Verificar turno de batalla o si ha terminado
            while (!pokeTAC.activeBattle.isBattlerOver())
            {
                
                Trainer nextTrainer = pokeTAC.activeBattle.nextTrainer();

                if (nextTrainer==pokeTAC.aiTrainer)
                {
                    pokeTAC.selectAIMove();
                }
                else
                {
                    //GUI: Seleccionar movimiento usuario
                    pokeTAC.askUserMove();
                }

                pokeTAC.activeBattle.proccessTurnLogic();
            }

            //GUI: Mostrar pantalla resultado
            
            pokeTAC.endBattle();
            
            
        } while (askIfBattleAgain());
            
        //GUI: Mostar pantalla despedida
        
        pokeTAC.endGame();
        
    }
    
    static boolean askIfBattleAgain()
    {
        System.out.println("Deseas jugar otra vez? [s/n]:");
        
        String ans = System.console().readLine();
        
        return ans.equalsIgnoreCase("s");
    }
    
    // << Constants >>
    
    final int MAX_POKEMON = 4; //Cantidad de pokemons por entrenador
    final int MAX_MOVES = 4; //Cantidad de moviminetos por pokemon
    
    // << InternalFields >>
    List<PokeInfo> pokemonDB;
    
    Battle activeBattle;
    Trainer userTrainer;
    Trainer aiTrainer;
    
    Random rnd;
    
    // << InternalMethods >>
    
    private void initGame(String username)
    {
        //Cargar datos de archivo
        try {
            loadDataPokeInfo();
        }
        catch(IOException e){
            System.out.println("error:PokeInfo loader");
            e.printStackTrace();
            
        }
        try {
            loadDataPokeMovements();
        }
        catch(IOException e){
            System.out.println("error:PokeMovements loader");
            e.printStackTrace();
        }
        
        //Crear todo lo necesario para el AI
        //createAI();
        
        userTrainer = new Trainer(username);
        aiTrainer = new Trainer("IA");
        
        rnd = new Random();
       
    }
    private void loadDataPokeMovements() throws IOException{
        
    }
    private void loadDataPokeInfo()throws IOException
    {
        //................ Cargar data pokeInfo
        pokemonDB = new ArrayList<PokeInfo>();
        FileReader reader;
        File arch=null;
        String line1,line2,line3;
        BufferedReader br;
        arch=new File("./Files/pokedex.txt");
        reader=new FileReader(arch);
        br=null;
        br=new BufferedReader(reader);
        PokeInfo newPoke;
        ArrayList<PokeType> types;
        PokeType single_type;
        int numPokemon=20;
        String[]values;
        int[] converted_value;
        String[]arr_types;
        for(int i=0;i<numPokemon;i++){
            line1=br.readLine();
            line2=br.readLine();
            line3=br.readLine();
            newPoke= new PokeInfo();
            types=new ArrayList<PokeType>();
            newPoke.setNombre(line1);
            arr_types=line2.split("/");
            
            for(int j=0;j<arr_types.length;j++){
                single_type=new PokeType();
                single_type.setNombre(arr_types[j]);
                types.add(single_type);
            }
            newPoke.setTipos(types);
            
            values=line3.split(" ");
            converted_value=new int[values.length];
            for(int k=0;k<values.length;k++){
                converted_value[k] = Integer.parseInt(values[k]);
            }
            newPoke.setHp(converted_value[0]);
            newPoke.setAtaque(converted_value[1]);
            newPoke.setDefensa(converted_value[2]);
            newPoke.setVelocidad(converted_value[5]);
            pokemonDB.add(newPoke);
        }
        reader.close();
        //................ fin Cargar data pokeInfo
    }
    
    //Aqui se selecciona los pokemons para la IA
    private void selectAITeam()
    {
        List<Pokemon> ipokemons = new ArrayList<>();
        
        for (int i = 0; i < MAX_POKEMON; i++) {
            
            PokeInfo pokemon = pokemonDB.get(rnd.nextInt(pokemonDB.size()));
            
            List<Movement> moves = new ArrayList<>();
            
            for (int j = 0; j < MAX_MOVES; j++) {
                
                List<Movement> pokeMoves = pokemon.getMoves();
                
                Movement move = pokeMoves.get(rnd.nextInt(pokeMoves.size()));              
                
                moves.add(move);
            }
            
            ipokemons.add(new Pokemon(pokemon,moves));
        }
                
        aiTrainer.setTeam(ipokemons);
    }
    
    private void initBattle()
    {
        activeBattle = new Battle(userTrainer,aiTrainer);
    }
 
    private void selectAIMove()
    {
        if (rnd.nextBoolean())
        {
            //CambiarPokemon
            aiTrainer.changePokemon(aiTrainer.getTeam().get(rnd.nextInt(MAX_POKEMON)));
        }
        else
        {
            //Escojer ataque
            aiTrainer.setNextMove(aiTrainer.getActivePokemon().getMovimientos().get(MAX_MOVES));
        }     
    }
    
    private void endBattle()
    {
        activeBattle = null;
    }
    
    private void endGame()
    {
        
    }

    private void askUserMove() {
                
        if (rnd.nextBoolean())
        {
            //CambiarPokemon
            userTrainer.changePokemon(userTrainer.getTeam().get(rnd.nextInt(MAX_POKEMON)));
        }
        else
        {
            //Escojer ataque
            userTrainer.setNextMove(userTrainer.getActivePokemon().getMovimientos().get(MAX_MOVES));
        } 
        
    }

    private void askUserTeam() {
        
        List<Pokemon> ipokemons = new ArrayList<>();
        
        for (int i = 0; i < MAX_POKEMON; i++) {
            
            PokeInfo pokemon = pokemonDB.get(rnd.nextInt(pokemonDB.size()));
            
            List<Movement> moves = new ArrayList<>();
            
            for (int j = 0; j < MAX_MOVES; j++) {
                
                List<Movement> pokeMoves = pokemon.getMoves();
                
                Movement move = pokeMoves.get(rnd.nextInt(pokeMoves.size()));              
                
                moves.add(move);
            }
            
            ipokemons.add(new Pokemon(pokemon,moves));
        }
        
        userTrainer.setTeam(ipokemons);
                
    }
    
    
    
}
