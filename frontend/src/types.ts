export type Quiz = {
    id: number;
    title: string;
    description: string;
    category: {
      id: number;
      title: string;
    };
    course: string;
    isPublished: boolean;
    addedOn: string;
  };