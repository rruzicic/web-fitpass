const vm = new Vue({
	el: '#app',
	data: {
		currentUsername: null,
		coachTrainings: [],
		trainingsMap: [],
		nameSportObjectSearch: '',
		startPriceSearch: null,
		endPriceSearch: null,
		startDateSearch: null,
		endDateSearch: null,
		startDateSearch: null,
		endDateSearch: null,
		sportObjectTypeFilter: "all",
		trainingTypeFilter: "all",
		sortIndex: null,
		sportsObject: null,
		training: null,
		price: null
	},
	created(){
		axios.get('rest/currentUser')
			.then((response) => {
				this.currentUsername = response.data.username;
				axios.get(
				'rest/training-history/trainings/coach',
				{
					params: {
						username: this.currentUsername
					}
				}
				)		
			.then((response) => {
				this.coachTrainings = response.data;
				for(let i = 0; i < this.coachTrainings.length; i++){
					axios.get(
						'rest/objects/training',
						{
							params:{
								trainingId: this.coachTrainings[i].trainingId
							}
						}
					)
					.then((response) => {
						
						let add = true;
						for(let i = 0; i < this.trainingsMap.length; i++){
							if(this.trainingsMap[i].id == response.data.trainingId){
								add = false;
							}
						}
						if(add){
							this.trainingsMap.push({id: this.coachTrainings[i].trainingId, training:response.data})
						}
						
						//this.trainingsMap.push({id: this.coachTrainings[i].trainingId, training:response.data})
					})
					
				}
			})
		})
		
	},
	methods: {
		findTraining(id) {
			return this.trainingsMap.filter(function(trainingId){
				return trainingId.id == id;
			})
		},
		removeTraining(id) {
			
			axios.delete(
						'rest/training-history/trainings/coach-remove-training',
						{
							params:{
								trainingHistoryId: id
							}
						}
					)
			window.location.reload()
		}
	},
	computed:{
		filteredCoachTrainings:function(){
			
			return this.coachTrainings.filter((trainingH) => {
				let keep = true;
				let maxPrice = 10000;
					if(this.endPriceSearch != null || this.endPriceSearch == ""){
						maxPrice = this.endPriceSearch;
					}
					if(maxPrice == ""){
						maxPrice = 10000;
					}
					
					for(let i = 0; i < this.trainingsMap.length; ++i){
						if(this.trainingsMap[i].id == trainingH.trainingId){
							this.training = this.trainingsMap[i].training;
							this.price = this.trainingsMap[i].price;
							keep = keep && this.training.sportsObject.toLowerCase().match(this.nameSportObjectSearch.toLowerCase())
								 && (Number(this.training.price) >= Number(this.startPriceSearch))
								 && (Number(this.training.price) <= maxPrice);
							break;
						}
					}
					/*
				axios.get(
						'rest/objects/training',
						{
							params:{
								trainingId: trainingH.trainingId
							}
						}
					)
					.then((response) => {
						this.training = response.data;
						this.sportsObject = response.data.sportsObject;
						this.price = response.data.price;
						//keep = keep && (this.training.sportsObject.toLowerCase().match(this.nameSportObjectSearch.toLowerCase()))
						//return keep;
					})
					*/
					//keep = keep;
					//keep = keep && this.training.sportsObject.toLowerCase().match(this.nameSportObjectSearch.toLowerCase())
					//	    && (Number(this.training.price) >= Number(this.startPriceSearch));
					
					//if(this.sportsObject != null && this.price != null){
					//keep = keep && this.sportsObject.toLowerCase().match(this.nameSportObjectSearch.toLowerCase())
						    //&& (Number(this.price) >= Number(this.startPriceSearch));
							//&& (Number(this.training.price) <= maxPrice); //training.price.toString().match(this.startPriceSearch);
					//}
					
				return keep;
			})
			
		}
	}
	/*
	computed:{
		filteredCoachTrainings:function(){
			
			return this.coachTrainings.filter((trainingHistory) => {
				let keep = true;
				axios.get(
						'rest/objects/training',
						{
							params:{
								trainingId: trainingHistory.trainingId
							}
						}
					)
					.then((response) => {
						this.training = response.data;
						this.sportsObject = response.data.sportsObject;
						//keep = keep && this.training.sportsObject.toLowerCase().match(this.nameSportObjectSearch.toLowerCase())
					})
					let maxPrice = 10000;
					if(this.endPriceSearch != null || this.endPriceSearch == ""){
						maxPrice = this.endPriceSearch;
					}
					if(maxPrice == ""){
						maxPrice = 10000;
					}
					if(this.training != null){
						keep = keep && this.training.sportsObject.toLowerCase().match(this.nameSportObjectSearch.toLowerCase())
						    && (Number(this.training.price) >= Number(this.startPriceSearch));
							//&& (Number(this.training.price) <= maxPrice); //training.price.toString().match(this.startPriceSearch);
					}
				return keep;
			})
			
		}
	}
	*/
})