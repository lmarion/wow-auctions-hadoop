package auctions.model;

import java.util.List;

public class Alliance 
{
	private List<Auction> auctions;

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}
}
