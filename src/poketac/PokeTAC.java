/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poketac;

import java.util.ArrayList;
import java.util.List;
import modelo.Batalla;
import modelo.Entrenador;
import modelo.InstanciaPokemon;
import modelo.Movimiento;
import modelo.Pokemon;

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
            pokeTAC.userTrainer.setTeam(null);

            pokeTAC.initBattle();
            
            //GUI: Verificar turno de batalla
            while (!pokeTAC.activeBattle.isBattlerOver())
            {
                Entrenador nextTrainer = pokeTAC.activeBattle.nextTrainer();

                if (nextTrainer==pokeTAC.aiTrainer)
                {
                    pokeTAC.selectAIMove();
                }
                else
                {
                    //GUI: Seleccionar movimiento usuario
                    pokeTAC.userTrainer.selectNextMove(null,null);
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
    
    final int MAX_POKEMON = 4; //Cantidad de pokemons por entrenador
    final int MAX_MOVES = 4; //Cantidad de moviminetos por pokemon
    
    // << InternalFields >>
    List<Pokemon> pokemonDB;
    
    Batalla activeBattle;
    Entrenador userTrainer;
    Entrenador aiTrainer;
    
    
    // << InternalMethods >>
    
    private void initGame(String username)
    {
        //Cargar datos de archivo
        loadData();
        
        //Crear todo lo necesario para el AI
        //createAI();
        
        userTrainer = new Entrenador(username);
        aiTrainer = new Entrenador("IA");
        
        
       
    }
    
    private void loadData()
    {
        pokemonDB = new ArrayList<>();
        
        //................ Cargar data
    }
    
    //Aqui se selecciona los pokemons para la IA
    private void selectAITeam()
    {
        List<InstanciaPokemon> ipokemons = new ArrayList<>();
        
        for (int i = 0; i < MAX_POKEMON; i++) {
            
            Pokemon pokemon = null; //Random de pokemonDB
            
            List<Movimiento> moves = new ArrayList<>();
            
            for (int j = 0; j < MAX_MOVES; j++) {
                
                Movimiento move = null; //Random de pokemon.moves
                
                moves.add(move);
            }
            
            ipokemons.add(new InstanciaPokemon(pokemon,moves));
        }
        
        
        aiTrainer.setTeam(ipokemons);
    }
    
    private void initBattle()
    {
        activeBattle = new Batalla(userTrainer,aiTrainer);
    }
 
    private void selectAIMove()
    {
        InstanciaPokemon iPokemon = null; //Random de aiTrainer.ipokemons
        
        Movimiento move = null; //random de pokemon.moves
        
        aiTrainer.selectNextMove(iPokemon,move);
    }
    
    private void endBattle()
    {
        activeBattle = null;
    }
    
    private void endGame()
    {
        
    }
    
    
    
}
