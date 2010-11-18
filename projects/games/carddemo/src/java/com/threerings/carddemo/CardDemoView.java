//
// $Id$

package com.threerings.carddemo;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.threerings.media.image.ImageManager;
import com.threerings.media.image.Mirage;
import com.threerings.media.tile.TileManager;
import com.threerings.media.tile.UniformTileSet;

import com.threerings.parlor.card.data.Card;
import com.threerings.parlor.card.client.CardPanel;

import com.threerings.toybox.util.ToyBoxContext;

/**
 * Displays a basic card view.
 */
public class CardDemoView extends CardPanel
{
    /** The size of the cards. */
    public static final Dimension CARD = new Dimension(60, 80);

    /** The size of the micro-cards. */
    public static final Dimension MICRO_CARD = new Dimension(21, 31);

    public CardDemoView (ToyBoxContext ctx, CardDemoController ctrl)
    {
        super(ctx.getFrameManager());

        _tmgr = new TileManager(new ImageManager(ctx.getResourceManager(), this));

        _cards = _tmgr.loadTileSet(CARDS_PATH, CARD.width, CARD.height).getTileMirages();
        _mcards = _tmgr.loadTileSet(MICRO_CARDS_PATH, MICRO_CARD.width, MICRO_CARD.height).
            getTileMirages();
    }

    @Override // from CardPanel
    public Mirage getCardBackImage ()
    {
        return _cards[13];
    }

    @Override // from CardPanel
    public Mirage getCardImage (Card card)
    {
        return _cards[getMirageIndex(card)];
    }

    @Override // from CardPanel
    public Mirage getMicroCardBackImage ()
    {
        return _mcards[13];
    }

    @Override // from CardPanel
    public Mirage getMicroCardImage (Card card)
    {
        return _mcards[getMirageIndex(card)];
    }

    @Override // from JComponent
    public Dimension getPreferredSize ()
    {
        return new Dimension(600, 600);
    }

    /**
     * Returns the index within the card mirage array of the specified card.
     */
    protected int getMirageIndex (Card card)
    {
        if (!card.isValid() || card.getNumber() == RED_JOKER) {
            return 2*14 + 13; // red joker at end of third line
        } else if (card.getNumber() == BLACK_JOKER) {
            return 3*14 + 13; // black joker at end of fourth line
        } else {
            return card.getSuit()*14 + (card.getNumber()-2); // 2,3,...,A on each line for S,H,C,D
        }
    }

    /** Used to load tiled images. */
    protected TileManager _tmgr;

    /** The card mirages. */
    protected Mirage[] _cards;

    /** The micro-card mirages. */
    protected Mirage[] _mcards;

    /** The path to the cards image. */
    protected static final String CARDS_PATH = "cards.png";

    /** The path of the micro-cards image. */
    protected static final String MICRO_CARDS_PATH = "cards_micro.png";
}