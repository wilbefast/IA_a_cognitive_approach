/** @author William J.D. **/

/*
Naughts and Crosses HTML 5 game example
Copyright (C) 2012 William James Dyce

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

Game.WHITE = 0;
Game.BLACK = 1;
Game.VICTORY_WHITE = 2;
Game.VICTORY_BLACK = 3;
Game.DRAW = 4;

Game.IMAGE_WHITE = load_image("york.png");
Game.IMAGE_BLACK = load_image("tudor.png");

Game.C_BACKGROUND = 'rgb(128,128,128)';
Game.C_TEXT = 'rgb(0,0,0)';

function Game()
{
    /** ATTRIBUTES **/
    
    // receiver 
    var obj = this;
    var typ = Game;
    
    // true attributes
    var board = null;
    var current_turn;
    var n_players = 2;
    var id = null;
    var is_human = [true, true];

    /** SUBROUTINES **/
    var xml_parse_state = function(attribute)
    {
        switch(attribute)
        {
            case "TURN_WHITE":
                return Game.WHITE;
            case "TURN_BLACK":
                return Game.BLACK;
            case "VICTORY_WHITE":
                return Game.VICTORY_WHITE;
            case "VICTORY_BLACK":
                return Game.VICTORY_BLACK;
            case "DRAW":
            default:
                return Game.DRAW;
        }
    }
    
    var redraw = function()
    {
        // redraw the game board
        board.redraw();

        // redraw the state information
        context_info.fillStyle = Game.C_BACKGROUND;
        context_info.fillRect(0,0,canvas_info.width, canvas_info.height);

        var image = null;
        var text = null;
        switch(current_turn)
        {
            case typ.WHITE:
                text = "York turn";
                image = typ.IMAGE_WHITE;
                break;

            case typ.BLACK:
                text = "Tudor turn";
                image = typ.IMAGE_BLACK;
                break;

            case typ.VICTORY_WHITE:
                text = "York victory!";
                image = typ.IMAGE_WHITE;
                break;

            case typ.VICTORY_BLACK:
                text = "Tudor victory!";
                image = typ.IMAGE_BLACK;
                break;

            case typ.VICTORY_BLACK:
                text = "Draw...";
                break;

            default:
                break;
        }
        
        // draw icon of winning/current name
        if(image != null)
            context_info.drawImage(image, canvas_info.width-canvas_info.height, 
                                    0, canvas_info.height, canvas_info.height);

        // draw player name
        if(text != null)
        {
            // always the same font, alignment and so on
            context_info.fillStyle = typ.C_TEXT;
            context_info.font = "16pt Arial";
            context_info.textAlign = "right";
            context_info.textBaseline = "middle";

            context_info.fillText(text, canvas_info.width-canvas_info.height,
                                                    canvas_info.height/2);
        }
        
        // draw game id
        context_info.textAlign = "left";
        context_info.textBaseline = "middle";
        context_info.fillText("Client "+id, 16, canvas_info.height/2);
        
        
    }

    /** METHODS **/
    obj.update_from_xml = function(data)
    {
        /** Parse new game state **/
        current_turn = xml_parse_state(data[0].getAttribute('state'));
        id = Number(data[0].getAttribute('id'));
        
        /** Parse new board state **/
        // create the board if it doesn't already exist
        if(board == null)
            board = new Board();
        // update board using the 'board' element of the XML document
        board.update_from_xml(data[0].childNodes[0]);
        
        /** Update the view to take changes into account **/
        redraw();
    }

    obj.clickEvent = function(x, y)
    {
        // restart the game if it's a draw or somebody has won
        if(current_turn >= n_players)
        {
            ajax_request_restart(id);
            board.clear();
            redraw();
            return;	// consume the event
        }

        // check if it's the human player's turn
        if (!is_human[current_turn])
        {
            alert("wait your turn");
            return;
        }

        // get the board position pointed to
        var row = board.y_to_row(y);
        var col = board.x_to_col(x);

        // the server will check if the move is legal
        ajax_request_move(id, row, col, current_turn);
    }

    /** INITIALISE **/
    ajax_request_id();

    /** RETURN INSTANCE **/
    return obj;
}