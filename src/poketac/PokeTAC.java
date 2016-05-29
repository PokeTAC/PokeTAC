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
        loadData();
        
        //Crear todo lo necesario para el AI
        //createAI();
        
        userTrainer = new Trainer(username);
        aiTrainer = new Trainer("IA");
        
        rnd = new Random();
       
    }
    
    private void loadData()
    {
        pokemonDB = new ArrayList<>();
        
        //................ Cargar data
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
