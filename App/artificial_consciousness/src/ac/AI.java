package ac;

import ac.analysis.Analysis;
import ac.frontier.Frontier;
import ac.frontier.morpion.MorpionFrontier;
import ac.memory.ActiveMemory;
import ac.reasoning.Reasoning;

/**
 * 
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class AI
{

  /* **************************************************************************
   * ATTRIBUTS
   * ************************************************************************ */

  private Analysis _analysis;
  private ActiveMemory _memory;
  private Reasoning _reasoning;
  private Frontier _frontier;

  /* **************************************************************************
   * CONSTRUCTOR
   * ************************************************************************ */

  public AI()
  {
    this._memory = new ActiveMemory();
    this._frontier = new MorpionFrontier();
    this._reasoning = new Reasoning(this._memory, this._frontier);
    this._analysis = new Analysis(this._memory, this._reasoning, this._frontier);
    
  }

  /* **************************************************************************
   * GETTERS
   * ************************************************************************ */

  public ActiveMemory getMemory()
  {
    return _memory;
  }

  public Reasoning getReasoning()
  {
    return _reasoning;
  }

  public Frontier getFrontier()
  {
    return _frontier;
  }

}