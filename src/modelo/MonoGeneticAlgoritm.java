/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import poketac.PokeTAC;

/**
 *
 * @author Franco
 */
    public class MonoGeneticAlgoritm
    {
        //#region << ExternalFields >>

        Indv[] initialPopulation;
        public Indv[] getInitialPopulation()
        {
                return initialPopulation;
        }

        int crossPoint;
        public int getCrossPoint()
        {
            return crossPoint;
        }
        public void setCrossPoint(int value)
        {
            crossPoint = value;
        }

        double mutateProbability;
        public double getMutateProbability()
        {
            return mutateProbability;
        }
        public void setMutateProbability(double value)
        {
            mutateProbability = value;
        }
        //#endregion


        //#region << InternaFields >>

        Random rnd;
        PokeTAC poketac;

        //#endregion


        public MonoGeneticAlgoritm(int crossPoint, double mutateProbability, int randomSeed)
        {
            //Iniciar campos
            this.crossPoint = crossPoint;
            this.mutateProbability = mutateProbability;
            initialPopulation = new Indv[0];
            if (randomSeed < 0) rnd = new Random(); else rnd = new Random(randomSeed);
            
            //Iniciar poketac para evaluar el fitness
            poketac = new PokeTAC();
            poketac.initGame("AI_0","AI_1");
        }


        //#region << ExternalMethods >>

        public void CreateInitialPopulation(int numWeights, int population)
        {
            initialPopulation = new Indv[population];
            
            for (int i = 0; i < population; i++)
            {
                double[] chromosome = new double[numWeights];

                for (int ii = 0; ii < chromosome.length; ii++)
                {
                    //El peso se inicia con un numero entre 0 y 1
                    chromosome[ii] = rnd.nextDouble();
                }

                initialPopulation[i] = new Indv(chromosome);
            }
            
            ProccessFitness(initialPopulation);
        }
    

        public Indv[] ProccessGenerations(int numGenerations)
        {
            return ProccessGenerations(Integer.MAX_VALUE, numGenerations);
        }

        public Indv[] ProccessGenerations(int minFitness, int maxGenerations)
        {

            Indv[] individuals = (Indv[])initialPopulation.clone();

            for (int n = 0; n < maxGenerations; n++)
            {
                //== Obtener a los N hijos
                int sumFitness = 0; boolean _break = false;
                for (Indv indiv : individuals)
                {
                    int fitness = indiv.getFitness();

                    if (fitness >= minFitness)
                    {
                        _break = true; break;
                    }
                        
                    sumFitness += fitness;
                }
                if (_break) break;

                //Crear N hijos
                Indv[] childs = new Indv[individuals.length];
                for (int i = 0; i < individuals.length; i++)
                {
                    //Escojer 2 padres aleatorios en proporcion al fitness
                    Indv parent1 = GetRandomByFitness(individuals, sumFitness);
                    Indv parent2 = GetRandomByFitness(individuals, sumFitness);
                    //Obtener 2 hijos
                    Indv[] _2childs = parent1.Cross(parent2, crossPoint);
                    //Mutar un hijo
                    if (rnd.nextDouble() < mutateProbability && n < maxGenerations - 1)
                    {
                        MutateIndividual(_2childs[rnd.nextInt(_2childs.length)]);
                    }
                    //Guardar hijos
                    childs[i++] = _2childs[0];
                    if (i < individuals.length) childs[i] = _2childs[1];
                }

                individuals = childs;
                
                //Batallar entre ellos para obtener el Fitness
                ProccessFitness(individuals);
            }           
            
            return individuals;
        }

        //#endregion

        
        //#region << InternalMethods >>

        private Indv GetRandomByFitness(Indv[] indiv, int sumFitness)
        {
            int rndVal = rnd.nextInt(sumFitness);

            for (int i = 0; i < indiv.length; i++)
            {
                if (rndVal < indiv[i].getFitness()) //if (rndVal - indiv[i].Fitness < indiv[i].Fitness)
                    return indiv[i];
                else
                    rndVal -= indiv[i].getFitness();
            }

            return null;
        }

        private void MutateIndividual(Indv individual)
        {
            int chrToChange = rnd.nextInt(individual.getChromosome().length);

            individual.getChromosome()[chrToChange] = rnd.nextDouble();
        }

        private void ProccessFitness(Indv[] individuals)
        {
            for (int i = 0; i < individuals.length; i++) {
                for (int j = 0; j < individuals.length; j++) {
                    if (i!=j)
                    {   
                        //Batalla entre individuals[i] y individuals[j]
                        Indv winner = Battle(individuals[i],individuals[j]);
                        winner.fitness++;
                    }
                }
            }            
        }
        
        private Indv Battle(Indv a, Indv b)
        {       
            //¿Los poquemon y sus movimientos deben ser los mismos para ambos jugadores?
            //¿Se debe usar los diferentes poquemon en cada batalla?
                        
            int winTrainer = poketac.weightedAutoBattle(a.chromosome,b.chromosome);
            
            if (winTrainer==0)
            {
                return a;
            }
            else
            {
                return b;
            }
        }
        
        //#endregion

    }

