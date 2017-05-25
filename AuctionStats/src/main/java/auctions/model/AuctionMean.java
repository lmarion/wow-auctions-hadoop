package auctions.model;

public class AuctionMean 
{
	private String item; //itemid
	private String meanBuyoutStr;
	private long meanBid;
	private long meanBuyout;
	private int meanQuantity;
	private String standardDeviation;
	private String petSpeciesId;
	private String petBreedId;
	private String petLevel;
	private String petQualityId;

	public AuctionMean()
	{
		
	}
	
	
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}

	public String getMeanBuyoutStr() {
		return meanBuyoutStr;
	}
	public void setMeanBuyoutStr(String meanBuyoutStr) {
		this.meanBuyoutStr = meanBuyoutStr;
	}


	public long getMeanBid() {
		return meanBid;
	}
	public void setMeanBid(long meanBid) {
		this.meanBid = meanBid;
	}
	public long getMeanBuyout() {
		return meanBuyout;
	}
	public void setMeanBuyout(long meanBuyout) {
		this.meanBuyout = meanBuyout;
	}
	public int getMeanQuantity() {
		return meanQuantity;
	}
	public void setMeanQuantity(int meanQuantity) {
		this.meanQuantity = meanQuantity;
	}
	
	public String getStandardDeviation() {
		return standardDeviation;
	}
	public void setStandardDeviation(String standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	public String getPetSpeciesId() {
		return petSpeciesId;
	}
	public void setPetSpeciesId(String petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}
	public String getPetBreedId() {
		return petBreedId;
	}
	public void setPetBreedId(String petBreedId) {
		this.petBreedId = petBreedId;
	}
	public String getPetLevel() {
		return petLevel;
	}
	public void setPetLevel(String petLevel) {
		this.petLevel = petLevel;
	}
	public String getPetQualityId() {
		return petQualityId;
	}
	public void setPetQualityId(String petQualityId) {
		this.petQualityId = petQualityId;
	}
}
